package org.tenten.bittakotlin.jobpost.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import org.tenten.bittakotlin.jobpost.entity.PayStatus
import org.tenten.bittakotlin.jobpost.entity.WorkCategory
import java.time.LocalDate
import java.time.LocalDateTime

@Data
@Builder
@AllArgsConstructor
@Schema(title = "일거리 DTO", description = "일거리의 요청 및 응답에 사용하는 DTO입니다.")
data class JobPostDTO(
    @Schema(title = "일거리 ID (PK)", description = "일거리의 고유 ID 입니다.", example = "1")
    @Min(1)
    val id: Long? = null,

    @Schema(title = "회원 ID (FK)", description = "회원의 고유 ID 입니다.", example = "1")
    @Min(1)
    @field:NotNull(message = "회원 ID가 필요합니다")
    val profileId: Long? = null,

    @Schema(title = "일거리 제목", description = "일거리 제목입니다.", example = "Job Title")
    @field:NotBlank(message = "제목 입력은 필수적 입니다")
    val title: String? = null,

    @Schema(title = "일거리 내용", description = "일거리 내용입니다.", example = "Job Content")
    @field:NotBlank(message = "설명 입력은 필수적 입니다")
    val description: String? = null,

    @Schema(title = "출근지", description = "출근 지역입니다.", example = "서울 특별시 광진구일대")
    @field:NotNull(message = "지역명은 필수적으로 입력해야 합니다")
    val location: String? = null,

    @Schema(title = "지불 유형", description = "지불 유형입니다.", example = "FREE")
    @field:NotNull(message = "급여 여부는 필수적으로 입력해야 합니다")
    val payStatus: PayStatus? = null,

    @Schema(title = "일거리 등록일시", description = "일거리가 등록된 날짜 및 시간입니다.", example = "2023-09-24T14:45:00")
    val createdAt: LocalDateTime? = null,

    @Schema(title = "일거리 수정일시", description = "일거리가 수정된 날짜 및 시간입니다.", example = "2023-09-24T14:45:00")
    val updateAt: LocalDateTime? = null,

    @Schema(title = "촬영 방법", description = "일거리의 진행 방식입니다.", example = "SNAPSHOT")
    @field:NotNull(message = "작품 카테고리는 필수적으로 입력해야 합니다")
    val workCategory: WorkCategory? = null,

    @Schema(title = "오디션일", description = "오디션을 진행하는 날짜입니다.", example = "2023-09-24")
    val auditionDate: LocalDate? = null,

    @Schema(title = "촬영 시작일", description = "일이 시작하는 날짜입니다.", example = "2023-09-24")
    val startDate: LocalDate? = null,

    @Schema(title = "촬영 종료일", description = "일이 종료되는 날짜입니다.", example = "2023-09-24")
    val endDate: LocalDate? = null,

    @Schema(title = "모집 종료일", description = "모집 종료 일자입니다.", example = "2024-09-24")
    val closeDate: LocalDate? = null,

    @Schema(title = "마감까지 남은 일자", description = "모집 종료까지 남은 일자입니다.", example = "38")
    val restDate: Int? = null
)

