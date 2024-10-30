package org.tenten.bittakotlin.profile.controller

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.tenten.bittakotlin.profile.dto.ProfileDTO
import org.tenten.bittakotlin.profile.service.ProfileService



@RestController
@RequestMapping("/api/v1/profile")
class ProfileController(
    private val profileService: ProfileService
) {

    @PostMapping
    fun create(@RequestBody profileDTO: ProfileDTO): ResponseEntity<ProfileDTO> {
        logger.info("Received request to create profile for memberId=${profileDTO.memberId}")
        val response = ResponseEntity.ok(profileService.createProfile(profileDTO))
        logger.info("Profile created successfully for memberId=${profileDTO.memberId}")
        return response
    }

    @GetMapping("/{memberId}")
    fun get(@PathVariable memberId: Long): ResponseEntity<ProfileDTO> {
        logger.info("Received request to get profile for memberId=$memberId")
        val response = ResponseEntity.ok(profileService.getProfile(memberId))
        logger.info("Profile fetched successfully for memberId=$memberId")
        return response
    }

    @PutMapping("/{memberId}")
    fun update(@PathVariable memberId: Long, @RequestBody profileDTO: ProfileDTO): ResponseEntity<ProfileDTO> {
        logger.info("Received request to update profile for memberId=$memberId")
        val response = ResponseEntity.ok(profileService.updateProfile(memberId, profileDTO))
        logger.info("Profile updated successfully for memberId=$memberId")
        return response
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(ProfileController::class.java)
    }
}