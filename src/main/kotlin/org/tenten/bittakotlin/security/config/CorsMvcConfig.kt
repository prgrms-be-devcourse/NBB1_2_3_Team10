package org.tenten.bittakotlin.security.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
class CorsMvcConfig : WebMvcConfigurer {

    override fun addCorsMappings(corsRegistry: CorsRegistry) {
        corsRegistry.addMapping("/**")
            .exposedHeaders("Set-Cookie")
            .allowedOrigins("http://localhost:3000")
    }
}