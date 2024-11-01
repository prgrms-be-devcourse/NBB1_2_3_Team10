package org.tenten.bittakotlin.chat.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime

class ChatResponseDto {
    data class Read(
        @field:Min(value = 1, message = "인덱스는 0 또는 음수가 될 수 없습니다.")
        val chatId: Long,

        @field:NotBlank(message = "발신자 닉네임이 비어있습니다.")
        val sender: String,

        @field:NotBlank(message = "메시지가 비어있습니다.")
        val message: String,
        
        val deleted: Boolean,

        val chatAt: LocalDateTime
    )

    data class Send(
        @field:Min(value = 1, message = "인덱스는 0 또는 음수가 될 수 없습니다.")
        val chatRoomId: Long,

        val activated: Boolean,

        @field:NotBlank(message = "비어있는 메시지를 전송할 수 없습니다.")
        val detail: Detail
    )

    data class Delete(
        @field:Min(value = 1, message = "인덱스는 0 또는 음수가 될 수 없습니다.")
        val chatRoomId: Long,

        @field:Min(value = 1, message = "인덱스는 0 또는 음수가 될 수 없습니다.")
        val chatId: Long,

        @field:NotBlank(message = "메시지가 비어있습니다.")
        val message: String
    )

    data class Detail(
        @field:Min(value = 1, message = "인덱스는 0 또는 음수가 될 수 없습니다.")
        val chatId: Long,

        @field:NotBlank(message = "발신자 닉네임이 비어있습니다.")
        val sender: String,

        @field:NotBlank(message = "메시지가 비어있습니다.")
        val message: String,

        val chatAt: LocalDateTime
    )
}