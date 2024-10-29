package org.tenten.bittakotlin.member.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(title = "회원 요청 DTO", description = "회원 관련 요청에 사용하는 DTO입니다.")
class MemberRequestDTO {

    @Schema(title = "회원가입 DTO", description = "회원가입 요청에 사용하는 DTO입니다.")
    data class Join(
        @Schema(title = "아이디", description = "회원가입에 사용할 아이디입니다.", example = "username")
        val username: String,

        @Schema(title = "비밀번호", description = "회원가입에 사용할 비밀번호입니다.", example = "password")
        val password: String,

        @Schema(title = "별명", description = "회원가입에 사용할 별명입니다.", example = "nickname")
        val nickname: String,

        @Schema(title = "주소", description = "회원의 주소입니다.", example = "경기도 고양시 일산동구 중앙로 1256")
        val address: String
    )

    @Schema(title = "로그인 DTO", description = "로그인 요청에 사용하는 DTO 입니다.")
    data class Login(
        @Schema(title = "아이디", description = "로그인에 사용할 아이디입니다.", example = "username")
        val username: String,

        @Schema(title = "비밀번호", description = "로그인에 사용할 비밀번호입니다.", example = "password")
        val password: String
    )

    @Schema(title = "비밀번호 변경 DTO", description = "비밀번호 변경 요청에 사용하는 DTO입니다.")
    data class ChangePassword(
        @Schema(title = "회원 ID (PK)", description = "변경할 회원의 기본키입니다.", example = "1")
        val id: Long,

        @Schema(title = "이전 비밀번호", description = "이전에 사용하던 비밀번호입니다.", example = "password1")
        val beforePassword: String,

        @Schema(title = "새로운 비밀번호", description = "새롭게 변경할 비밀번호입니다.", example = "password1")
        val afterPassword: String
    )

    @Schema(title = "회원정보 변경 DTO", description = "회원정보 변경 요청에 사용하는 DTO입니다.")
    data class Modify(
        @Schema(title = "회원 ID (PK)", description = "변경할 회원의 기본키입니다.", example = "1")
        val id: Long,

        @Schema(title = "아이디", description = "비밀번호를 변경할 아이디입니다.", example = "username")
        val username: String,

        @Schema(title = "새로운 별명", description = "새롭게 변경할 별명입니다.", example = "nickname")
        val nickname: String,

        @Schema(title = "새로운 주소", description = "새롭게 변경할 회원의 주소입니다.", example = "경기도 고양시 일산동구 중앙로 1256")
        val address: String
    )
}