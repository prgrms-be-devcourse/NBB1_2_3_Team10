package org.tenten.bittakotlin.security.service

import io.jsonwebtoken.ExpiredJwtException
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.tenten.bittakotlin.security.entity.RefreshEntity
import org.tenten.bittakotlin.security.jwt.JWTUtil
import org.tenten.bittakotlin.security.repository.RefreshRepository
import java.util.Date

@Service
class ReissueServiceImpl(
    private val jwtUtil: JWTUtil,
    private val refreshRepository: RefreshRepository
) : ReissueService {

    override fun reissueTokens(request: HttpServletRequest, response: HttpServletResponse): ResponseEntity<Any?> {
        val refresh = extractRefreshToken(request)
        if (refresh == null) {
            return ResponseEntity("refresh token null", HttpStatus.BAD_REQUEST)
        }

        try {
            jwtUtil.isExpired(refresh)
        } catch (e: ExpiredJwtException) {
            return ResponseEntity("refresh token expired", HttpStatus.BAD_REQUEST)
        }

        if (!isValidRefreshToken(refresh)) {
            return ResponseEntity("invalid refresh token", HttpStatus.BAD_REQUEST)
        }

        val username = jwtUtil.getUsername(refresh)
        val role = jwtUtil.getRole(refresh)

        val newAccess = jwtUtil.createJwt("access", username, role, 600000L)
        val newRefresh = jwtUtil.createJwt("refresh", username, role, 86400000L)

        updateRefreshToken(refresh, username, newRefresh)
        setResponseTokens(response, newAccess, newRefresh)

        return ResponseEntity(HttpStatus.OK)
    }

    private fun extractRefreshToken(request: HttpServletRequest): String? {
        return request.cookies?.firstOrNull { it.name == "refresh" }?.value
    }

    private fun isValidRefreshToken(refresh: String): Boolean {
        val category = jwtUtil.getCategory(refresh)
        return category == "refresh" && refreshRepository.existsByRefresh(refresh)
    }

    private fun updateRefreshToken(oldRefresh: String, username: String, newRefresh: String) {
        refreshRepository.deleteByRefresh(oldRefresh)
        addRefreshEntity(username, newRefresh, 86400000L)
    }

    private fun addRefreshEntity(username: String, refresh: String, expiredMs: Long) {
        val date = Date(System.currentTimeMillis() + expiredMs)
        val refreshEntity = RefreshEntity(
            expiration = date.toString(),
            refresh = refresh,
            username = username
        )
        refreshRepository.save(refreshEntity)
    }

    private fun setResponseTokens(response: HttpServletResponse, access: String, refresh: String) {
        response.setHeader("access", access)
        response.addCookie(createCookie("refresh", refresh))
    }

    private fun createCookie(key: String, value: String): Cookie {
        return Cookie(key, value).apply {
            maxAge = 24 * 60 * 60
            isHttpOnly = true
        }
    }
}