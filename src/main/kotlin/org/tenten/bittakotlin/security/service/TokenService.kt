package org.tenten.bittakotlin.security.service

import org.tenten.bittakotlin.security.dto.TokenRequestDto
import org.tenten.bittakotlin.security.dto.TokenResponseDto

interface TokenService {
    fun create(requestDto: TokenRequestDto.Create): TokenResponseDto.Create

    fun reissue(refreshToken: String): String
}