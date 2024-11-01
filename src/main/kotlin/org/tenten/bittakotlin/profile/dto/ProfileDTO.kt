package org.tenten.bittakotlin.profile.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class ProfileDTO(
    @field:NotNull(message = "회원 ID는 누락될 수 없습니다.")
    val memberId: Long,

    @field:NotBlank(message = "닉네임은 비워둘 수 없습니다.")
    @field:Size(max = 20, message = "닉네임은 최대 20자까지 입력할 수 있습니다.")
    val nickname: String,

    val profileUrl: String? = null,
    val description: String? = null,
    val job: String? = null,
    val socialMedia: String? = null
)