package org.tenten.bittakotlin.security.config

import JWTFilter
import jakarta.servlet.http.HttpServletRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.logout.LogoutFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.tenten.bittakotlin.member.repository.MemberRepository
import org.tenten.bittakotlin.security.jwt.CustomLogoutFilter
import org.tenten.bittakotlin.security.jwt.JWTUtil
import org.tenten.bittakotlin.security.jwt.LoginFilter
import org.tenten.bittakotlin.security.repository.RefreshRepository

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val authenticationConfiguration: AuthenticationConfiguration,
    private val jwtUtil: JWTUtil,
    private val refreshRepository: RefreshRepository
) {

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

        // CSRF disable
        http.csrf { it.disable() }

        // Form 로그인 방식 disable
        http.formLogin { it.disable() }

        // HTTP Basic 인증 방식 disable
        http.httpBasic { it.disable() }

        http.authorizeHttpRequests { auth ->
            auth
                .requestMatchers(
                    "/",
                    "/api/v1/member/login",
                    "/member/login",
                    "/api/v1/member/join",
                    "/member/join",
                    "/api/v1/member/reissue").permitAll()

                .requestMatchers(
                    "/api/v1/member/{id}/**",
                    "member/{id}/**",
                    "/api/v1/job-post/**",
                    "/job-post/**",
                    "/api/v1/like/**").hasRole("USER")

                .requestMatchers(HttpMethod.DELETE,"/api/v1/member/{id}").authenticated()
                .requestMatchers(HttpMethod.PUT,"/api/v1/member/{id}").authenticated()
                .requestMatchers("/api/v1/chat/**").authenticated()

                .anyRequest().authenticated()
        }

        http.addFilterBefore(JWTFilter(jwtUtil), LoginFilter::class.java)

        val loginFilter = LoginFilter(authenticationManager(), jwtUtil, refreshRepository)
        loginFilter.setFilterProcessesUrl("/api/v1/member/login")
        http.addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter::class.java)

        http.addFilterBefore(CustomLogoutFilter(jwtUtil, refreshRepository), LogoutFilter::class.java)

        // 세션 설정
        http.sessionManagement { session ->
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }

        return http.build()
    }
}