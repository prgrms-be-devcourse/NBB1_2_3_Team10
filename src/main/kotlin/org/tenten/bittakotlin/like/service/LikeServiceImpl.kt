package org.tenten.bittakotlin.like.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.tenten.bittakotlin.jobpost.exception.JobPostException
import org.tenten.bittakotlin.jobpost.repository.JobPostRepository
import org.tenten.bittakotlin.like.entity.Like
import org.tenten.bittakotlin.like.exception.LikeException
import org.tenten.bittakotlin.like.repository.LikeRepository
import org.tenten.bittakotlin.member.dto.MemberNicknameDTO
import java.time.LocalDateTime

@Service
class LikeServiceImpl(
    private val likeRepository: LikeRepository,
    private val profileRepository: ProfileRepository,
    private val jobPostRepository: JobPostRepository
) : LikeService {

    @Transactional
    override fun addLike(jobPostId: Long, memberId: Long) {
        val jobPost = jobPostRepository.findById(jobPostId).orElseThrow { LikeException.NOT_FOUND.get() }
        val profile = profileRepository.findById(profileId).orElseThrow { LikeException.NOT_FOUND.get() }

        if (!likeRepository.existsByJobPostAndProfile(jobPost, profile)) {
            val like = Like().apply {
                this.jobPost = jobPost
                this.profile = profile
                this.likedAt = LocalDateTime.now()
            }
            likeRepository.save(like)
        }
    }

    @Transactional
    override fun getLikesForJobPost(jobPostId: Long): List<MemberNicknameDTO> {
        val jobPost = jobPostRepository.findById(jobPostId).orElseThrow { RuntimeException() }
        return likeRepository.findByJobPost(jobPost).map { like ->
            MemberNicknameDTO(like.profile.nickname)
        }
    }

    @Transactional
    override fun removeLike(jobPostId: Long, memberId: Long) {
        val like = likeRepository.findByJobPostIdAndProfileId(jobPostId, profileId)
            .orElseThrow { JobPostException.NOT_FOUND.get() } as Like

        like.jobPost = null
        like.profile = null

        likeRepository.delete(like)
    }
}



