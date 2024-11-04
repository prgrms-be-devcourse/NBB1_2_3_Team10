package org.tenten.bittakotlin.profile.service

import org.tenten.bittakotlin.member.entity.Member
import org.tenten.bittakotlin.profile.dto.ProfileDTO
import org.tenten.bittakotlin.profile.entity.Profile

interface ProfileService {
    fun getProfile(profileId: Long): ProfileDTO
    fun updateProfile(profileId: Long, profileDTO: ProfileDTO): ProfileDTO
    fun createDefaultProfile(member: Member): ProfileDTO

    fun getByNickname(nickname: String): Profile
}