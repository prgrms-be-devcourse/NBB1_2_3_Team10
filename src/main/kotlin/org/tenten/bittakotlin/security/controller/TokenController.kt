package org.tenten.bittakotlin.security.controller

import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.tenten.bittakotlin.security.service.TokenService

@RestController
@RequestMapping("/api/v1/token")
class TokenController(
    private val tokenService: TokenService
) {
    @PostMapping
    fun reissue(request: HttpServletRequest, response: HttpServletResponse): ResponseEntity<Map<String, Any>> {
        val refreshToken = request.getHeader("Authority")

        val headers = HttpHeaders().apply {
            add(HttpHeaders.SET_COOKIE, getCookieString("accessToken", tokenService.reissue(refreshToken)
                , 3600))
        }

        val body = mapOf("message" to "토큰을 재발급했습니다.")

        return ResponseEntity(body, headers, HttpStatus.OK)
    }

    private fun getCookieString(name: String, token: String?, ageMax: Int): String {
        return "$name=$token; Path=/; Max-Age=$ageMax; HttpOnly;"
    }
}