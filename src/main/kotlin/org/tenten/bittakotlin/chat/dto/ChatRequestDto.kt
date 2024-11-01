package org.tenten.bittakotlin.chat.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

class ChatRequestDto {
    data class Read(
        @field:NotBlank(message = "발신자 닉네임이 비어있습니다.")
        val sender: String,

        @field:NotBlank(message = "수신자 닉네임이 비어있습니다.")
        val receiver: String,
    )

    data class Send(
        @field:NotBlank(message = "발신자 닉네임이 비어있습니다.")
        val sender: String,

        @field:NotBlank(message = "수신자 닉네임이 비어있습니다.")
        val receiver: String,

        @field:NotBlank(message = "메시지가 비어있습니다.")
        val message: String
    )

    data class Update(
        @field:Min(value = 1, message = "인덱스는 0 또는 음수가 될 수 없습니다.")
        val chatId: Long,

        @field:NotBlank(message = "메시지가 비어있습니다.")
        val message: String
    )
}
