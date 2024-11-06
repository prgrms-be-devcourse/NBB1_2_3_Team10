package org.tenten.bittakotlin.security.dto

class TokenResponseDto {
    data class Create(
        val accessToken: String,
        val refreshToken: String
    )
}