package org.tenten.bittakotlin.security.dto

class TokenRequestDto {
    data class Create(
        val username: String,

        val role: String
    )
}
