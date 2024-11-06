package org.tenten.bittakotlin.security.exception

import org.tenten.bittakotlin.security.constant.TokenError

class TokenException (
    val code: Int,

    override val message: String
) : RuntimeException(message) {
    constructor(tokenError: TokenError) : this(
        code = tokenError.code,
        message = tokenError.message
    )
}