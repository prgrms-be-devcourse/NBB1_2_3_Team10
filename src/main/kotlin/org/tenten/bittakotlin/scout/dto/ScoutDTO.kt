package org.tenten.bittakotlin.scout.dto

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor
import java.time.LocalDateTime

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ScoutDTO {
    private val id: Long? = null
    private val feedId: Long? = null
    private val senderId: Long? = null
    private val receiverId: Long? = null
    private val description: String? = null
    private val sentAt: LocalDateTime? = null
}