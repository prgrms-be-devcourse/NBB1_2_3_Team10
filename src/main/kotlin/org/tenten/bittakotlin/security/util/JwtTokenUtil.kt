package org.tenten.bittakotlin.security.util

import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.util.*
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

@Component
class JwtTokenUtil (@Value("\${spring.jwt.secret}") secret: String) {
    private val secretKey: SecretKey = SecretKeySpec(
        secret.toByteArray(StandardCharsets.UTF_8),
        Jwts.SIG.HS256.key().build().algorithm
    )

    fun isExpired(token: String): Boolean {
        return Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .payload
            .expiration
            .before(Date())
    }

    fun getUsername(token: String): String {
        return Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .payload
            .get("username", String::class.java)
    }

    fun getRole(token: String): String {
        return Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .payload
            .get("role", String::class.java)
    }

    fun generateAccessToken(username: String, role: String, currentMills: Long): String {
        return Jwts.builder()
            .claims(mapOf(
                "username" to username,
                "role" to role
            ))
            .issuedAt(Date(currentMills))
            .expiration(Date(currentMills + 3600000))
            .signWith(secretKey)
            .compact()
    }

    fun generateRefreshToken(currentMills: Long): String {
        return Jwts.builder()
            .issuedAt(Date(currentMills))
            .expiration(Date(currentMills + 604800000))
            .signWith(secretKey)
            .compact()
    }
}