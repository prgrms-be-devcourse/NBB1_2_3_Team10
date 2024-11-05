package org.tenten.bittakotlin.calendar.dto

import java.time.LocalDate
import java.time.LocalDateTime
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import io.swagger.v3.oas.annotations.media.Schema

@Schema(title = "캘린더 DTO", description = "회원의 일정을 확인할 때 사용하는 DTO입니다.")
data class EventCalendarDTO(
    @Schema(title = "캘린더 ID (PK)", description = "캘린더의 고유 ID 입니다.", example = "1", minimum = "1")
    @field:Min(value = 1, message = "ID는 음수가 될 수 없습니다")
    val id: Long? = null,

    @Schema(title = "회원 ID", description = "회원의 고유 ID 입니다.", example = "1", minimum = "1")
    @field:Min(value = 1, message = "ID는 음수가 될 수 없습니다")
    @field:NotNull(message = "회원의 ID가 필요합니다")
    val profileId: Long? = null,

    val startDate: LocalDate? = null,
    val endDate: LocalDate? = null,
    val auditionDate: LocalDateTime? = null,
    val title: String? = null
)
