package org.tenten.bittakotlin.feed.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor

class FeedRequestDto {
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    class Modify {
        @Schema(title = "피드 ID (PK)", description = "피드의 고유 ID 입니다.", example = "1", minimum = "1")
        val id: @Min(value = 1, message = "ID는 0 또는 음수가 될 수 없습니다.") Long? = null

        @Schema(title = "피드 제목", description = "피드 제목입니다.", example = "Feed Title", minimum = "1", maximum = "50")
        val title: @NotBlank(message = "제목은 비우거나, 공백이 될 수 없습니다.") @Size(
            min = 1,
            max = 50,
            message = "제목은 1 ~ 50자 이하여야 합니다."
        ) String? = null

        @Schema(title = "피드 내용", description = "피드 내용입니다.", example = "Feed Content")
        @Builder.Default
        val content: @NotNull String = ""
    }
}