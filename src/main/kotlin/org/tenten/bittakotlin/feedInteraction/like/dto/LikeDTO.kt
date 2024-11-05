package org.tenten.bittakotlin.feedInteraction.like.dto

data class LikeDTO(
    val feedId: Long?,
    val profileId: Long?,
    val isLiked: Boolean
)