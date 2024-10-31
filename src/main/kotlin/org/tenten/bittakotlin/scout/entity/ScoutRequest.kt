package org.tenten.bittakotlin.scout.entity

//import com.prgrms2.java.bitta.feed.entity.Feed
//import com.prgrms2.java.bitta.member.entity.Member
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@EntityListeners(AuditingEntityListener::class)
data class ScoutRequest(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "feed_id", nullable = false)
    val feed: Feed,

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