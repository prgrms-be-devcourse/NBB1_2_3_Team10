package org.tenten.bittakotlin.feedInteraction.feedLike.service

import org.tenten.bittakotlin.feedInteraction.feedLike.dto.FeedLikeDTO

interface FeedLikeService {
    fun toggleLike(feedId: Long, profileId: Long): FeedLikeDTO
    fun getLikeCount(feedId: Long): Long
}
