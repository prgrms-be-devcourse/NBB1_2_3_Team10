package org.tenten.bittakotlin.feedInteraction.feedLike.service

import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.tenten.bittakotlin.feed.repository.FeedRepository
import org.tenten.bittakotlin.feedInteraction.feedLike.dto.FeedLikeDTO
import org.tenten.bittakotlin.feedInteraction.feedLike.entity.FeedLike
import org.tenten.bittakotlin.feedInteraction.feedLike.repository.FeedLikeRepository
import org.tenten.bittakotlin.profile.repository.ProfileRepository


@Service
class FeedLikeServiceImpl(
    private val likeRepository: FeedLikeRepository,
    private val feedRepository: FeedRepository,
    private val profileRepository: ProfileRepository
) : FeedLikeService {

    @Transactional
    override fun toggleLike(feedId: Long, profileId: Long): FeedLikeDTO {
        val feed = feedRepository.findById(feedId)
            .orElseThrow { EntityNotFoundException("Feed not found for id: $feedId") }
        val profile = profileRepository.findById(profileId)
            .orElseThrow { EntityNotFoundException("Profile not found for id: $profileId") }

        val like = likeRepository.findByFeedAndProfile(feed, profile).orElseGet {
            val newLike = FeedLike(feed = feed, profile = profile, liked = true)
            likeRepository.save(newLike)
            newLike
        }

        like.liked = !like.liked
        likeRepository.save(like)

        return FeedLikeDTO(feed.id, profile.id, like.liked)
    }

    @Transactional(readOnly = true)
    override fun getLikeCount(feedId: Long): Long {
        val feed = feedRepository.findById(feedId)
            .orElseThrow { EntityNotFoundException("Feed not found for id: $feedId") }

        return likeRepository.countByFeedAndLikedTrue(feed)
    }
}