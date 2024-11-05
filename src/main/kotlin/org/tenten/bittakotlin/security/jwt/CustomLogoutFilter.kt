package org.tenten.bittakotlin.security.jwt

import io.jsonwebtoken.ExpiredJwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.aspectj.weaver.tools.cache.SimpleCacheFactory.path
import org.slf4j.LoggerFactory
import org.springframework.web.filter.GenericFilterBean
import org.tenten.bittakotlin.security.repository.RefreshRepository
import java.io.IOException

class CustomLogoutFilter(
    private val jwtUtil: JWTUtil,
    private val refreshRepository: RefreshRepository
) : GenericFilterBean() {

    private val logger = LoggerFactory.getLogger(CustomLogoutFilter::class.java)

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        doFilter(request as HttpServletRequest, response as HttpServletResponse, chain)
    }

    @Throws(IOException::class, ServletException::class)
    private fun doFilter(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val requestUri = request.requestURI
        logger.info("Incoming request URI: $requestUri")

        if (!requestUri.matches("^/api/v1/member/logout$".toRegex())) {
            logger.warn("Invalid logout request URI: $requestUri")
            filterChain.doFilter(request, response)
            return
        }

        val requestMethod = request.method
        if (requestMethod != "POST") {
            logger.warn("Invalid request method: $requestMethod")
            filterChain.doFilter(request, response)
            return
        }

        val refresh = request.cookies?.firstOrNull { it.name == "refresh" }?.value
        if (refresh == null) {
            logger.error("Refresh token is missing")
            response.status = HttpServletResponse.SC_BAD_REQUEST
            return
        }

        try {
            if (jwtUtil.isExpired(refresh)) {
                logger.error("Refresh token is expired")
                response.status = HttpServletResponse.SC_BAD_REQUEST
                return
            }
        } catch (e: ExpiredJwtException) {
            logger.error("ExpiredJwtException: ${e.message}")
            response.status = HttpServletResponse.SC_BAD_REQUEST
            return
        }

        val category = jwtUtil.getCategory(refresh)
        if (category != "refresh") {
            logger.error("Invalid token category: $category")
            response.status = HttpServletResponse.SC_BAD_REQUEST
            return
        }

        val isExist = refreshRepository.existsByRefresh(refresh)
        if (!isExist) {
            logger.error("Refresh token does not exist in the database")
            response.status = HttpServletResponse.SC_BAD_REQUEST
            return
        }

        // Proceed with logout
        refreshRepository.deleteByRefresh(refresh)
        val cookie = Cookie("refresh", null).apply {
            maxAge = 0
            path = "/"
        }
        response.addCookie(cookie)
        logger.info("Successfully logged out and deleted refresh token")
        response.status = HttpServletResponse.SC_OK
    }
}