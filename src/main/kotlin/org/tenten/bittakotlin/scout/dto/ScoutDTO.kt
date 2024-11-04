package org.tenten.bittakotlin.scout.dto

import java.time.LocalDateTime

data class ScoutDTO(
    val id: Long? = null,
    val feedId: Long,
    val senderId: Long,
    val receiverId: Long,
    val description: String? = null,
    val sentAt: LocalDateTime? = null
)