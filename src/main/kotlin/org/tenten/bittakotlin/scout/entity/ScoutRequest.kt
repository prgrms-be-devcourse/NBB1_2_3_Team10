package org.tenten.bittakotlin.scout.entity

import com.prgrms2.java.bitta.feed.entity.Feed
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import lombok.AccessLevel
import lombok.Builder
import lombok.Data
import lombok.Setter
import java.time.LocalDateTime

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(
    AuditingEntityListener::class
)
class ScoutRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private val id: Long? = null

    @ManyToOne
    @JoinColumn(name = "feed_id", nullable = false)
    private val feed: Feed? = null

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private val sender: Member? = null

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private val receiver: Member? = null

    @Lob
    private val description: String? = null

    @CreatedDate
    @Column(updatable = false)
    private var sentAt: LocalDateTime? = null
}