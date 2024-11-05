package org.tenten.bittakotlin.chat.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@EntityListeners(AuditingEntityListener::class)
data class ChatRoom(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(columnDefinition = "TINYINT(1) DEFAULT 1", nullable = false)
    var activated: Boolean = true,

    @CreatedDate
    @Column(updatable = false, nullable = false)
    val createdAt: LocalDateTime?,

    @LastModifiedDate
    @Column(nullable = false)
    val updatedAt: LocalDateTime?
) {
    constructor(): this(
        createdAt = null,
        updatedAt = null
    )
}
