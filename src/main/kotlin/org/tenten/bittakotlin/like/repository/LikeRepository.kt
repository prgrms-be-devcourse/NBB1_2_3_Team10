package org.tenten.bittakotlin.like.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.tenten.bittakotlin.jobpost.entity.JobPost
import org.tenten.bittakotlin.like.entity.Like
import java.util.Optional

interface LikeRepository : JpaRepository<Like, Long> {

    @Query("SELECT l FROM Like l WHERE l.jobPost = :jobPost")
    fun findByJobPost(jobPost: JobPost): List<Like>

    @Query("SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END FROM Like l WHERE l.jobPost = :jobPost AND l.profile = :profile")
    fun existsByJobPostAndProfile(jobPost: JobPost, profile: Profile): Boolean

    @Query("SELECT l FROM Like l WHERE l.jobPost.id = :jobPostId AND l.profile.id = :profileId")
    fun findByJobPostIdAndProfileId(jobPostId: Long, profileId: Long): Optional<Any>
}
