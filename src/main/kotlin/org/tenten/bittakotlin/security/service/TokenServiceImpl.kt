package org.tenten.bittakotlin.security.service

import org.springframework.stereotype.Service
import org.tenten.bittakotlin.security.constant.TokenError
import org.tenten.bittakotlin.security.dto.TokenRequestDto
import org.tenten.bittakotlin.security.dto.TokenResponseDto
import org.tenten.bittakotlin.security.entity.RefreshEntity
import org.tenten.bittakotlin.security.exception.TokenException
import org.tenten.bittakotlin.security.repository.RefreshRepository
import org.tenten.bittakotlin.security.util.JwtTokenUtil
import java.util.Date

@Service
class TokenServiceImpl(
    private val refreshRepository: RefreshRepository,

    private val jwtTokenUtil: JwtTokenUtil
) : TokenService {
    override fun create(requestDto: TokenRequestDto.Create): TokenResponseDto.Create {
        val currentMills = System.currentTimeMillis()

        val sevenDaysAfter = Date(currentMills + 604800000)

        val accessToken = jwtTokenUtil.generateAccessToken(requestDto.username, requestDto.role, currentMills)

        val refreshToken = jwtTokenUtil.generateRefreshToken(currentMills)

        refreshRepository.save(RefreshEntity(
            username = requestDto.username,
            refresh = refreshToken,
            expiration = sevenDaysAfter.toString()
        ))

        return TokenResponseDto.Create(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    override fun reissue(refreshToken: String): String {
        if (jwtTokenUtil.isExpired(refreshToken)) {
            throw TokenException(TokenError.REFRESH_EXPIRED)
        }

        val refresh = refreshRepository.findByRefresh(refreshToken)
            .orElseThrow { TokenException(TokenError.REFRESH_NOT_FOUND) }

        return jwtTokenUtil.generateAccessToken(refresh.username, "ROLE_USER", System.currentTimeMillis())
    }
}