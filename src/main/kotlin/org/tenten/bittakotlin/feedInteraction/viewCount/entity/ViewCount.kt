package org.tenten.bittakotlin.feedInteraction.viewCount.entity

import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor
import org.tenten.bittakotlin.feed.entity.Feed


@Entity
class ViewCount(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id", nullable = false)
    var feed: Feed,

    @Column(name = "count", nullable = false)
    var count: Long = 0L
)