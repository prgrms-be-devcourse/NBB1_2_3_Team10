package org.tenten.bittakotlin.like.service

interface LikeService {
    fun addLike(jobPostId: Long, profileId: Long)
    fun getLikesForJobPost(jobPostId: Long): List<MemberNicknameDTO>
    fun removeLike(jobPostId: Long, profileId: Long)
}

