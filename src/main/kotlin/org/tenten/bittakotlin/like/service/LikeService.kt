package org.tenten.bittakotlin.like.service

import org.tenten.bittakotlin.member.dto.MemberNicknameDTO

interface LikeService {
    fun addLike(jobPostId: Long, profileId: Long)
    fun getLikesForJobPost(jobPostId: Long): List<MemberNicknameDTO>
    fun removeLike(jobPostId: Long, profileId: Long)
}

