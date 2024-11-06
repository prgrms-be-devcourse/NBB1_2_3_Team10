package org.tenten.bittakotlin.security.filter

import io.jsonwebtoken.ExpiredJwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.core.annotation.Order
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import org.springframework.util.AntPathMatcher
import org.springframework.web.filter.OncePerRequestFilter
import org.tenten.bittakotlin.security.service.CustomUserDetailsService
import org.tenten.bittakotlin.security.util.JwtTokenUtil
import java.io.IOException
import java.lang.Exception

@Order(0)
@Component
class JwtAuthenticationFilter(
    private val jwtTokenUtil: JwtTokenUtil,

    private val customUserDetailsService: CustomUserDetailsService
) : OncePerRequestFilter() {
    private val publicUrls = arrayOf("/", "/api/v1/member/login", "/api/v1/member/join", "/api/v1/token",
        "/swagger", "/swagger-ui.html", "/swagger-ui/**", "/api-docs", "/api-docs/**", "/v3/api-docs/**")

    private val pathMatcher = AntPathMatcher()

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val requestUri = request.requestURI

        if (publicUrls.any { pathMatcher.match(it, requestUri) }) {
            filterChain.doFilter(request, response)
            return
        }

        val accessToken: String? = request.getHeader("Authority")

        if (accessToken != null) {
            try {
                if (jwtTokenUtil.isExpired(accessToken)) {
                    response.status = HttpServletResponse.SC_UNAUTHORIZED
                    response.writer.print("Access token has expired.")
                    return
                }

                val username: String = jwtTokenUtil.getUsername(accessToken)
                val userDetails: UserDetails? = customUserDetailsService.loadUserByUsername(username)

                if (userDetails == null) {
                    response.status = HttpServletResponse.SC_UNAUTHORIZED
                    response.writer.print("No matching member exists.")
                    return
                }

                logger.info("username=${userDetails.username}, authority${userDetails.authorities}")

                val authentication: Authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                SecurityContextHolder.getContext().authentication = authentication

                logger.info("adsgassdgasdgasdg")
            } catch (e: ExpiredJwtException) {
                response.status = HttpServletResponse.SC_UNAUTHORIZED
                response.writer.print("Access token has expired.")
                return
            } catch (e: Exception) {
                response.status = HttpServletResponse.SC_UNAUTHORIZED
                response.writer.print("Token authentication failed.")
                return
            }
        } else {
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            response.writer.print("The access token does not exist.")
            return
        }

        filterChain.doFilter(request, response)
    }
}