package org.tenten.bittakotlin.media.dto

import jakarta.validation.constraints.NotBlank

class MediaResponseDto {
    data class Read (
        @field:NotBlank(message = "조회 링크가 비어있습니다.")
        val link: String
    )

    data class Upload (
        @field:NotBlank(message = "업로드 링크가 비어있습니다.")
        val link: String
    )
}