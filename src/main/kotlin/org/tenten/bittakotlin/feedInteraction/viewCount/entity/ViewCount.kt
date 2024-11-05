package org.tenten.bittakotlin.feedInteraction.viewCount.entity

import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor
import org.tenten.bittakotlin.feed.entity.Feed


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class ViewCount(
    @field:JoinColumn(
        name = "feed_id",
        nullable = false
    ) @field:OneToOne(fetch = FetchType.LAZY) var feed: Feed, count: Long
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long = 0

    @Column(name = "count", nullable = false)
    private var count = 0L

    init {
        this.count = count
    }
}
