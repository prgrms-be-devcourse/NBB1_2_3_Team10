package org.tenten.bittakotlin.media.dto

import org.tenten.bittakotlin.media.entity.Media

class MediaResponseDto {
    data class Read (
        val filename: String,

        val url: String
    )

    data class Upload (
        val url: String,

        val media: Media
    )

    data class PublicUpload (
        val uploadUrl: String,

        val readUrl: String
    )
}