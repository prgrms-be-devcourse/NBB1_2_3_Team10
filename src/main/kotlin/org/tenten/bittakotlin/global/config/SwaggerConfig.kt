package org.tenten.bittakotlin.global.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@OpenAPIDefinition
@Configuration
class SwaggerConfig {

    @Bean
    fun openAPI(): OpenAPI {
        return OpenAPI().info(
            Info().title("BitTa Kotlin")
                .version("1.0")
                .description("Bitta Kotlin")
        )
    }
}