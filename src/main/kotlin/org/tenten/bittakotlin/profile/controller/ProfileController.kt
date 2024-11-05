package org.tenten.bittakotlin.profile.controller

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.tenten.bittakotlin.profile.dto.ProfileDTO
import org.tenten.bittakotlin.profile.service.ProfileServiceImpl



@RestController
@RequestMapping("/api/v1/profile")
class ProfileController(
    private val profileService: ProfileServiceImpl
) {

    @GetMapping("/{profileId}")
    fun get(@PathVariable profileId: Long): ResponseEntity<ProfileDTO> {
        logger.info("Received request to get profile for profileId=$profileId")
        val response = ResponseEntity.ok(profileService.getProfile(profileId))
        logger.info("Profile fetched successfully for memberId=$profileId")
        return response
    }

    @PutMapping("/{profileId}")
    fun update(@PathVariable profileId: Long, @RequestBody profileDTO: ProfileDTO): ResponseEntity<ProfileDTO> {
        logger.info("Received request to update profile for profileId=$profileId")
        val updatedProfile = profileService.updateProfile(profileId, profileDTO)
        logger.info("Profile updated successfully for profileId=$profileId")
        return ResponseEntity.ok(updatedProfile)
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(ProfileController::class.java)
    }
}