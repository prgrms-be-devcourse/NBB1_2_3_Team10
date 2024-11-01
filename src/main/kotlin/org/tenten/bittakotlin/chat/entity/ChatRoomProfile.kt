package org.tenten.bittakotlin.chat.entity

import jakarta.persistence.Column
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne
import jakarta.persistence.MapsId
import org.tenten.bittakotlin.chat.entity.key.ChatRoomProfileId
import org.tenten.bittakotlin.profile.entity.Profile

@Entity
data class ChatRoomProfile(
    @EmbeddedId
    val id: ChatRoomProfileId? = null,

    @ManyToOne
    @MapsId("chatRoomId")
    val chatRoom: ChatRoom,

    @ManyToOne
    @MapsId("profileId")
    val profile: Profile,

    @Column(columnDefinition = "TINYINT(1) DEFAULT 0", nullable = false)
    var blocked: Boolean = false
)
