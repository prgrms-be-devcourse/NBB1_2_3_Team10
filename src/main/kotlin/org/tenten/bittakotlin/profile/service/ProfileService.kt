package org.tenten.bittakotlin.profile.service

import org.tenten.bittakotlin.profile.dto.ProfileDTO

interface ProfileService {
    fun createProfile(profileDTO: ProfileDTO): ProfileDTO
    fun getProfile(memberId: Long): ProfileDTO
    fun updateProfile(memberId: Long, profileDTO: ProfileDTO): ProfileDTO
}