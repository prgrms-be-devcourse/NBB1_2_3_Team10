package org.tenten.bittakotlin.media.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

class MediaRequestDto {
    data class Read (
        @field:NotBlank(message = "파일명이 비어있습니다.")
        val filename: String
    )

    data class Upload (
        @field:NotBlank(message = "파일명이 비어있습니다.")
        val filename: String,

        @field:Min(value = 0, message = "비어있는 파일은 업로드할 수 없습니다.")
        @field:NotBlank(message = "파일 크기가 비어있습니다.")
        val filesize: Int,

        @field:Pattern(regexp = "(image/(jpeg|png|gif|bmp|webp|svg\\+xml))|(video/(mp4|webm|ogg|x-msvideo|x-matroska))"
            , message = "지원하지 않는 파일 형식입니다.")
        @field:NotBlank(message = "파일 형식이 비어있습니다.")
        val mimetype: String
    )

    data class Delete (
        @field:NotBlank(message = "파일명이 비어있습니다.")
        val filename: String
    )
}