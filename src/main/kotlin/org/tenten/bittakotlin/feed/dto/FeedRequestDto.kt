package org.tenten.bittakotlin.feed.dto

import org.tenten.bittakotlin.media.dto.MediaRequestDto

class FeedRequestDto {
    data class Create (
        val title: String,

        val content: String,

        val medias: List<MediaRequestDto.Upload>?
    )

    data class Modify (
        val title: String,

        val content: String,

        val uploads: List<MediaRequestDto.Upload>?,

        val deletes: List<MediaRequestDto.Delete>?
    )
}