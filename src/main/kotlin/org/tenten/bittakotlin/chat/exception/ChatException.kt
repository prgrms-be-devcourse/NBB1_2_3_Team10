package org.tenten.bittakotlin.media.exception

import org.tenten.bittakotlin.chat.constant.ChatError
import org.tenten.bittakotlin.media.constant.MediaError

class ChatException(
    val code: Int,

    override val message: String
) : RuntimeException(message) {
    constructor(chatError: ChatError) : this(
        code = chatError.code,
        message = chatError.message
    )
}