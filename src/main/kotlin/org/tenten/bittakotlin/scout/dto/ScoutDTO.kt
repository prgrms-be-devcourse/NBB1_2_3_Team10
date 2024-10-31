package org.tenten.bittakotlin.scout.dto

import java.time.LocalDateTime

data class ScoutDTO(
    val id: Long? = null,
    val feedId: Long? = null,
    val senderId: Long? = null,
    val receiverId: Long? = null,
    val description: String? = null,
    val sentAt: LocalDateTime? = null
)