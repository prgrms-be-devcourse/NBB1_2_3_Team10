package org.tenten.bittakotlin.feed.entity

import org.tenten.bittakotlin.media.entity.Media
import org.tenten.bittakotlin.member.entity.Member
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
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

    @Lob
    @Column(nullable = false)
    var content: String,

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    var member: Optional<Member>,

    @CreatedDate
    @Column(updatable = false, nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @OneToMany(mappedBy = "feed", cascade = [CascadeType.REMOVE])
    var medias: MutableList<Media> = mutableListOf()
) {
    fun clearMedias() {
        medias.clear()
    }
}