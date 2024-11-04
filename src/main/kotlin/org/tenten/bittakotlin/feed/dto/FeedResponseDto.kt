package org.tenten.bittakotlin.feed.dto

import org.tenten.bittakotlin.media.dto.MediaResponseDto
import java.time.LocalDateTime

class FeedResponseDto {
    data class Read (
        val id: Long,

        val title: String,

        val content: String,

        val author: String,

        val createdAt: LocalDateTime,

        val medias: List<MediaResponseDto.Read>
    )

    data class Create (
        val medias: List<MediaResponseDto.Read>
    )

    data class Modify (
        val medias: List<MediaResponseDto.Read>
    )
}