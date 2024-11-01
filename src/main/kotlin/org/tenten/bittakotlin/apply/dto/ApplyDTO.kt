package org.tenten.bittakotlin.apply.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor
import org.tenten.bittakotlin.apply.entity.ApplyStatus
import java.time.LocalDateTime

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "지원서 DTO", description = "지원서 요청 및 응답에 사용하는 DTO입니다.")
data class ApplyDTO(

    @Schema(title = "지원서 ID (PK)", description = "지원서의 고유 ID 입니다.", example = "1", minimum = "1")
    @field:Min(value = 1, message = "ID는 음수가 될 수 없습니다")
    var id: Long? = null,

    @Schema(title = "일거리 ID (FK)", description = "일거리의 고유 ID 입니다.", example = "1", minimum = "1")
    @field:Min(value = 1, message = "ID는 음수가 될 수 없습니다")
    @field:NotNull(message = "게시글의 ID가 필요합니다")
    var jobPostId: Long? = null,

    @Schema(title = "프로필 ID (FK)", description = "회원의 고유 프로필 ID 입니다.", example = "1", minimum = "1")
    @field:Min(value = 1, message = "ID는 음수가 될 수 없습니다")
    @field:NotNull(message = "회원의 ID가 필요합니다")
    var profileId: Long? = null,

    @Schema(title = "지원서 생성일시", description = "지원서가 생성된 날짜 및 시간입니다.", example = "2023-09-24T14:45:00")
    var appliedAt: LocalDateTime? = null,

    @Schema(title = "지원 검토 상태", description = "지원서의 현재 검토 상태입니다", example = "PENDING")
    var status: ApplyStatus? = null
)

