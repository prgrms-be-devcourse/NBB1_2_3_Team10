package org.tenten.bittakotlin.chat.entity

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.tenten.bittakotlin.profile.entity.Profile
import java.time.LocalDateTime

@Entity
@EntityListeners(AuditingEntityListener::class)
data class Chat(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(length = 400, nullable = false)
    var message: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    val profile: Profile,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id")
    val chatRoom: ChatRoom,

    @Column(columnDefinition = "TINYINT(1) DEFAULT 0", nullable = false)
    var deleted: Boolean = false,

    @CreatedDate
    @Column(updatable = false, nullable = false)
    val createdAt: LocalDateTime?,

    @LastModifiedDate
    @Column(nullable = false)
    val updatedAt: LocalDateTime?
)
