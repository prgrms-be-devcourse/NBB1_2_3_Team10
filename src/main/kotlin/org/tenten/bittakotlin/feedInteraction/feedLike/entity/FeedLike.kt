package org.tenten.bittakotlin.feedInteraction.feedLike.entity

import jakarta.persistence.*
import org.tenten.bittakotlin.feed.entity.Feed
import org.tenten.bittakotlin.profile.entity.Profile

data class FeedLike(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id", nullable = false)
    var feed: Feed,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    var profile: Profile,

    @Column(nullable = false)
    var liked: Boolean = false
)