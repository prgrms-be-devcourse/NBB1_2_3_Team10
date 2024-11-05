package org.tenten.bittakotlin.profile.service

import org.tenten.bittakotlin.media.dto.MediaRequestDto
import org.tenten.bittakotlin.member.entity.Member
import org.tenten.bittakotlin.profile.dto.ProfileDTO
import org.tenten.bittakotlin.profile.entity.Profile

interface ProfileService {
    fun getProfile(profileId: Long): ProfileDTO
    fun updateProfile(profileId: Long, profileDTO: ProfileDTO): ProfileDTO
    fun createDefaultProfile(member: Member): ProfileDTO

    fun getByNickname(nickname: String): Profile

    fun getByPrincipal(): Profile

    fun updateProfileImage(requestDto: MediaRequestDto.Upload): String

    fun deleteProfileImage()
}