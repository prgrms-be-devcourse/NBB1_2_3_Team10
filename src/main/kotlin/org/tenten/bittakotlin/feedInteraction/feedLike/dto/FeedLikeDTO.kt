package org.tenten.bittakotlin.feedInteraction.feedLike.dto

data class FeedLikeDTO(
    val feedId: Long?,
    val profileId: Long?,
    val isLiked: Boolean
)