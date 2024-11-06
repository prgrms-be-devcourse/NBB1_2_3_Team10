package org.tenten.bittakotlin.security.constant

import org.springframework.http.HttpStatus

enum class TokenError(val code: Int, val message: String) {
    REFRESH_EXPIRED(HttpStatus.BAD_REQUEST.value(), "리프레시 토큰이 만료되었습니다."),
    REFRESH_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "리프레시 토큰의 인증정보가 존재하지 않습니다.")
}