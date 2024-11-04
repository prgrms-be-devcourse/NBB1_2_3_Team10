package org.tenten.bittakotlin.feed.entity

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.tenten.bittakotlin.profile.entity.Profile
import java.time.LocalDateTime
import java.util.*

@Entity
@EntityListeners(AuditingEntityListener::class)
data class Feed(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, length = 50)
    var title: String,

    @Column(nullable = false, length = 1000)
    var content: String,

    @ManyToOne
    @JoinColumn(name = "profile_id", nullable = false)
    val profile: Profile,

    @OneToMany(mappedBy = "feed", cascade = [CascadeType.ALL])
    val feedMedias: List<FeedMedia> = mutableListOf(),

    @CreatedDate
    @Column(updatable = false, nullable = false)
    val createdAt: LocalDateTime? = null,

    @LastModifiedDate
    @Column(updatable = true, nullable = false)
    var updatedAt: LocalDateTime? = null
)