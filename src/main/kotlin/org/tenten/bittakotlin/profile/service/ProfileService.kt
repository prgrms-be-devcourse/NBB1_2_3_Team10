package org.tenten.bittakotlin.profile.service

import org.tenten.bittakotlin.member.entity.Member
import org.tenten.bittakotlin.profile.dto.ProfileDTO

interface ProfileService {
    fun createProfile(memberId: Long, nickname: String): ProfileDTO
    fun getProfile(memberId: Long): ProfileDTO
    fun updateProfile(memberId: Long, profileDTO: ProfileDTO): ProfileDTO
    fun createDefaultProfile(member: Member): ProfileDTO
}