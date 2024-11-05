package org.tenten.bittakotlin.feedInteraction.viewCount.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.tenten.bittakotlin.feed.entity.Feed
import org.tenten.bittakotlin.feedInteraction.viewCount.entity.ViewCount
import java.util.*

interface ViewCountRepository : JpaRepository<ViewCount, Long> {
    fun findByFeed(feed: Feed): Optional<ViewCount>
}