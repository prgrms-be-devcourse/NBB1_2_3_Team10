package org.tenten.bittakotlin.feed.dto

import org.tenten.bittakotlin.media.dto.MediaRequestDto
import org.tenten.bittakotlin.media.dto.MediaResponseDto
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.*
import java.time.LocalDateTime

@Schema(title = "피드 DTO", description = "피드의 요청 및 응답에 사용하는 DTO입니다.")
data class FeedDTO(
    @field:Schema(title = "피드 ID (PK)", description = "피드의 고유 ID 입니다.", example = "1", minimum = "1")
    @field:Min(value = 1, message = "ID는 0 또는 음수가 될 수 없습니다.")
    var id: Long? = null,

    @field:Schema(title = "피드 제목", description = "피드 제목입니다.", example = "Feed Title", minimum = "1", maximum = "50")
    @field:NotBlank(message = "제목은 비우거나, 공백이 될 수 없습니다.")
    @field:Size(min = 1, max = 50, message = "제목은 1 ~ 50자 이하여야 합니다.")
    var title: String,

    @field:Schema(title = "피드 내용", description = "피드 내용입니다.", example = "Feed Content")
    @field:NotNull
    var content: String = "",

    @field:Schema(title = "회원 ID (FK)", description = "회원의 고유 ID 입니다.", example = "1", minimum = "1")
    @field:Min(value = 1, message = "ID는 0 또는 음수가 될 수 없습니다.")
    @field:NotNull(message = "회원 ID는 누락될 수 없습니다.")
    var memberId: Long,

    @field:Schema(title = "피드 생성일시", description = "피드가 생성된 날짜 및 시간입니다.", example = "2023-09-24T14:45:00")
    var createdAt: LocalDateTime? = null,

    @field:Schema(title = "미디어 파일 목록", description = "피드에 포함된 사진 및 영상 목록입니다.")
    var medias: List<String> = emptyList()
)