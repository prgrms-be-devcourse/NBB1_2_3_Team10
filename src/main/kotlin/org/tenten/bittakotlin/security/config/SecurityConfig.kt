package org.tenten.bittakotlin.security.config

import jakarta.servlet.http.HttpServletRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.tenten.bittakotlin.security.filter.JwtAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,

    private val authenticationConfiguration: AuthenticationConfiguration,
) {
    private val publicUrls = arrayOf("/", "/api/v1/member/login", "/api/v1/member/logout", "/api/v1/member/join", "/api/v1/token",
        "/swagger", "/swagger-ui.html", "/swagger-ui/**", "/api-docs", "/api-docs/**", "/v3/api-docs/**")

    private val signedUrls = arrayOf("/api/v1/member/{id}**", "/api/v1/job-post/**", "/job-post/**", "/api/v1/like/**",
        "/api/v1/chat/**", "/api/v1/feed")

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.cors { corsCustomizer ->
            corsCustomizer.configurationSource(object : CorsConfigurationSource {
                override fun getCorsConfiguration(request: HttpServletRequest): CorsConfiguration {
                    return CorsConfiguration().apply {
                        allowedOrigins = listOf("http://localhost:3000")
                        allowedMethods = listOf("*")
                        allowCredentials = true
                        allowedHeaders = listOf("*")
                        maxAge = 3600L
                        exposedHeaders = listOf("Set-Cookie", "access")
                    }
                }
            })
        }

        http.csrf { it.disable() }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers(*publicUrls).permitAll()
                    .requestMatchers(*signedUrls).hasRole("USER")
                    .anyRequest().authenticated()
            }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .addFilterBefore(jwtAuthenticationFilter, BasicAuthenticationFilter::class.java)


        /*http.addFilterAfter(JWTFilter(jwtUtil), LoginFilter::class.java)

        val loginFilter = LoginFilter(authenticationManager(), jwtUtil, refreshRepository)

        loginFilter.setFilterProcessesUrl("/api/v1/member/login")

        http.addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter::class.java)

        http.addFilterBefore(CustomLogoutFilter(jwtUtil, refreshRepository), LogoutFilter::class.java)*/

        return http.build()
    }
}
