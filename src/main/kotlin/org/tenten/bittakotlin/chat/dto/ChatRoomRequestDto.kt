package org.tenten.bittakotlin.chat.dto

import jakarta.validation.constraints.NotBlank

class ChatRoomRequestDto {
    data class Create(
        @field:NotBlank(message = "회원 닉네임이 비어있습니다.")
        val nickname1: String,

        @field:NotBlank(message = "회원 닉네임이 비어있습니다.")
        val nickname2: String
    )

    data class Block(
        @field:NotBlank(message = "차단 요청 회원의 닉네임은 비워둘 수 없습니다.")
        val requester: String,

        @field:NotBlank(message = "차단 대상 회원의 닉네임은 비워둘 수 없습니다.")
        val target: String
    )
}