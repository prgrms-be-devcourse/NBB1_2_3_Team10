package org.tenten.bittakotlin.feedInteraction.like.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.tenten.bittakotlin.feed.entity.Feed
import org.tenten.bittakotlin.feedInteraction.like.entity.Like
import org.tenten.bittakotlin.profile.entity.Profile
import java.util.*


interface LikeRepository : JpaRepository<Like, Long> {
    fun findByFeedAndProfile(feed: Feed, profile: Profile): Optional<Like>
    fun countByFeedAndLikedTrue(feed: Feed): Long
}
