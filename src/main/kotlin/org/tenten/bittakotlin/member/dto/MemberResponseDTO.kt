package org.tenten.bittakotlin.member.dto

import io.swagger.v3.oas.annotations.media.Schema


class MemberResponseDTO {

    @Schema(title = "회원정보 DTO", description = "회원정보 요청에 사용하는 DTO입니다.")
    data class Information(
        @Schema(title = "회원 ID (PK)", description = "조회한 회원의 기본키입니다.", example = "1")
        val id: Long,

        @Schema(title = "아이디", description = "조회한 회원의 아이디입니다.", example = "username")
        val username: String,

        @Schema(title = "닉네임", description = "조회한 회원의 별명입니다.", example = "nickname")
        val nickname: String,

        @Schema(title = "주소", description = "조회한 회원의 주소입니다.", example = "경기도 고양시 일산동구 중앙로 1256")
        val address: String
    )

}