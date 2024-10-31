package org.tenten.bittakotlin.media.exception

import org.tenten.bittakotlin.media.constant.MediaError

class MediaException(
    val code: Int,

    override val message: String
) : RuntimeException(message) {
    constructor(mediaError: MediaError) : this(
        code = mediaError.code,
        message = mediaError.message
    )
}