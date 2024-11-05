package org.tenten.bittakotlin.feedInteraction.like.service

import org.tenten.bittakotlin.feedInteraction.like.dto.LikeDTO

interface LikeService {
    fun toggleLike(feedId: Long, profileId: Long): LikeDTO
    fun getLikeCount(feedId: Long): Long
}
