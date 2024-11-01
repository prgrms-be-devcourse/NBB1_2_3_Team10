package org.tenten.bittakotlin.chat.entity.key

import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
data class ChatRoomProfileId(
    val chatRoomId: Long,

    val profileId: Long
) : Serializable
