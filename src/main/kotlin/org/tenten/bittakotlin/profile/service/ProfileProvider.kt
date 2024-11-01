package org.tenten.bittakotlin.profile.service

import org.springframework.stereotype.Service
import org.tenten.bittakotlin.profile.entity.Profile
import org.tenten.bittakotlin.profile.repository.ProfileRepository

@Service
class ProfileProvider(
    private val profileRepository: ProfileRepository
) {
    fun getById(id: Long): Profile? {
        return profileRepository.findById(id).orElse(null)
    }
}