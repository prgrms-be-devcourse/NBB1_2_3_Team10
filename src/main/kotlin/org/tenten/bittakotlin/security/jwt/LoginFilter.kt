package org.tenten.bittakotlin.security.jwt

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.util.StreamUtils
import org.tenten.bittakotlin.member.dto.MemberRequestDTO
import org.tenten.bittakotlin.profile.entity.Profile
import org.tenten.bittakotlin.profile.service.ProfileService

import org.tenten.bittakotlin.security.entity.RefreshEntity
import org.tenten.bittakotlin.security.repository.RefreshRepository
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.util.*

class LoginFilter(
    private val authenticationManager: AuthenticationManager,
    private val jwtUtil: JWTUtil,
    private val refreshRepository: RefreshRepository,
    private val profileService: ProfileService
) : UsernamePasswordAuthenticationFilter() {

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        val loginDTO = try {
            val objectMapper = ObjectMapper()
            val inputStream = request.inputStream
            val messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8)
            println(messageBody)
            objectMapper.readValue(messageBody, MemberRequestDTO.Login::class.java)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }

        val username = loginDTO.username
        val password = loginDTO.password

        println(username)

        val authToken = UsernamePasswordAuthenticationToken(username, password)

        return authenticationManager.authenticate(authToken)
    }

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authentication: Authentication
    ) {

        // 유저 정보
        val username = authentication.name
        val authorities = authentication.authorities
        val role = authorities.iterator().next().authority

        // 토큰 생성
        val access = jwtUtil.createJwt("access", username, role, 600000L)
        val refresh = jwtUtil.createJwt("refresh", username, role, 86400000L)

        // Refresh 토큰 저장
        addRefreshEntity(username, refresh, 86400000L)

        // 응답 설정
        response.setHeader("access", access)
        response.addCookie(createCookie("refresh", refresh))
        
        // 프로필 정보 설정
        val profile: Profile = profileService.getByPrincipal()
        response.addCookie(createPublicCookie("nickname", profile.nickname))
        response.addCookie(createPublicCookie("profileUrl", profile.profileUrl!!))
        
        response.status = HttpStatus.OK.value()
    }

    override fun unsuccessfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        failed: AuthenticationException
    ) {
        response.status = 401
    }

    /**이 코드 손봐야할 수도 있음 */
    private fun addRefreshEntity(username: String, refresh: String, expiredMs: Long) {
        val date = Date(System.currentTimeMillis() + expiredMs)

        val refreshEntity = RefreshEntity(
            username = username,
            refresh = refresh,
            expiration = date.toString()
        )

        refreshRepository.save(refreshEntity)
    }

    private fun createCookie(key: String, value: String): Cookie {
        return Cookie(key, value).apply {
            maxAge = 24 * 60 * 60
            // secure = true
            path = "/"
            isHttpOnly = true
        }
    }

    private fun createPublicCookie(key: String, value: String): Cookie {
        return Cookie(key, value).apply {
            maxAge = 24 * 60 * 60
            path = "/"
            isHttpOnly = false
        }
    }
}