package org.tenten.bittakotlin.scout.entity

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.tenten.bittakotlin.member.entity.Member
import java.time.LocalDateTime

@Entity
@EntityListeners(AuditingEntityListener::class)
data class ScoutRequest(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    //feed 마이그레이션 종료가 안되어 일단 주석처리 하겠습니다
    //@ManyToOne
    //@JoinColumn(name = "feed_id", nullable = false)
    //val feed: Feed,

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    val sender: Member,

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    val receiver: Member,

    @Lob
    val description: String? = null,

    @CreatedDate
    @Column(updatable = false)
    var sentAt: LocalDateTime? = null
)