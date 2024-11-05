package org.tenten.bittakotlin.feedInteraction.feedLike.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.tenten.bittakotlin.feed.entity.Feed
import org.tenten.bittakotlin.feedInteraction.feedLike.entity.FeedLike
import org.tenten.bittakotlin.profile.entity.Profile
import java.util.*


interface FeedLikeRepository : JpaRepository<FeedLike, Long> {
    fun findByFeedAndProfile(feed: Feed, profile: Profile): Optional<FeedLike>
    fun countByFeedAndLikedTrue(feed: Feed): Long
}
