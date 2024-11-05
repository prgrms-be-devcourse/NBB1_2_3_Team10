package org.tenten.bittakotlin.feedInteraction.like.service

import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.tenten.bittakotlin.feed.repository.FeedRepository
import org.tenten.bittakotlin.feedInteraction.like.dto.LikeDTO
import org.tenten.bittakotlin.feedInteraction.like.entity.Like
import org.tenten.bittakotlin.feedInteraction.like.repository.LikeRepository
import org.tenten.bittakotlin.profile.repository.ProfileRepository


@Service
class LikeServiceImpl(
    private val likeRepository: LikeRepository,
    private val feedRepository: FeedRepository,
    private val profileRepository: ProfileRepository
) : LikeService {

    @Transactional
    override fun toggleLike(feedId: Long, profileId: Long): LikeDTO {
        val feed = feedRepository.findById(feedId)
            .orElseThrow { EntityNotFoundException("Feed not found for id: $feedId") }
        val profile = profileRepository.findById(profileId)
            .orElseThrow { EntityNotFoundException("Profile not found for id: $profileId") }

        val like = likeRepository.findByFeedAndProfile(feed, profile).orElseGet {
            val newLike = Like(feed = feed, profile = profile, liked = true)
            likeRepository.save(newLike)
            newLike
        }

        like.liked = !like.liked
        likeRepository.save(like)

        return LikeDTO(feed.id, profile.id, like.liked)
    }

    @Transactional(readOnly = true)
    override fun getLikeCount(feedId: Long): Long {
        val feed = feedRepository.findById(feedId)
            .orElseThrow { EntityNotFoundException("Feed not found for id: $feedId") }

        return likeRepository.countByFeedAndLikedTrue(feed)
    }
}