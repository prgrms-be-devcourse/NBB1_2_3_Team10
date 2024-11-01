package org.tenten.bittakotlin.apply.dto

import io.swagger.v3.oas.annotations.media.Schema
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor
import org.tenten.bittakotlin.apply.entity.ApplyStatus

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "지원서 상태 DTO", description = "지원서의 현재 검토 상태 변경시 사용하는 DTO입니다.")
data class ApplyStatusUpdateDTO(
    @Schema(title = "지원서 ID", description = "지원서의 고유 ID입니다.", example = "1")
    var id: Long? = null,

    @Schema(title = "지원서 검토 상태", description = "지원서의 현재 검토 상태입니다.", example = "PENDING")
    var applyStatus: ApplyStatus? = null
)
