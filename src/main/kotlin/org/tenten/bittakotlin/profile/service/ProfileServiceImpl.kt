package org.tenten.bittakotlin.profile.service

import jakarta.persistence.EntityNotFoundException
import org.slf4j.LoggerFactory
import org.slf4j.Logger
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.tenten.bittakotlin.member.entity.Member
import org.tenten.bittakotlin.member.repository.MemberRepository
import org.tenten.bittakotlin.member.service.MemberService
import org.tenten.bittakotlin.profile.dto.ProfileDTO
import org.tenten.bittakotlin.profile.entity.Profile
import org.tenten.bittakotlin.profile.repository.ProfileRepository
import org.tenten.bittakotlin.profile.constant.Job


@Service
class ProfileServiceImpl(
    private val profileRepository: ProfileRepository,
) : ProfileService {

    //Member 생성시 Profile 도 같이 생성
    @Transactional
    override fun createDefaultProfile(member: Member): ProfileDTO {

        val nickname = generateUniqueNickname()
        val profile = Profile(
            member = member,
            nickname = nickname,
            profileUrl = null,
            description = "This is a default profile.",
            job = null,
            socialMedia = null
        )
        val savedProfile = profileRepository.save(profile)
        return toDto(savedProfile)
    }

    private fun generateUniqueNickname(): String {
        var nickname: String
        var counter = 1
        do {
            nickname = "default#$counter"
            counter++
        } while (profileRepository.findByNickname(nickname).isPresent)  // 중복 검사
        return nickname
    }


    @Transactional(readOnly = true)
    override fun getProfile(profileId: Long): ProfileDTO {
        logger.info("Fetching profile for profileId=$profileId")

        val profile = profileRepository.findById(profileId)
            .orElseThrow { EntityNotFoundException("Profile not found for profileId=$profileId") }

        logger.info("Profile fetched successfully for profileId=$profileId")
        return toDto(profile)
    }

    @Transactional
    override fun updateProfile(profileId: Long, profileDTO: ProfileDTO): ProfileDTO {
        val profile = profileRepository.findById(profileId)
            .orElseThrow { EntityNotFoundException("Profile not found with id=$profileId") }

        profileDTO.nickname?.let { newNickname ->
            if (profile.nickname != newNickname && profileRepository.findByNickname(newNickname).isPresent) {
                throw IllegalArgumentException("Nickname '$newNickname' is already in use")
            }
            profile.nickname = newNickname
        }

        profile.description = profileDTO.description ?: profile.description
        profile.socialMedia = profileDTO.socialMedia ?: profile.socialMedia
        profile.profileUrl = profileDTO.profileUrl ?: profile.profileUrl
        profile.job = profileDTO.job?.let { Job.valueOf(it) } ?: profile.job


        val updatedProfile = profileRepository.save(profile)
        return toDto(updatedProfile)
    }

    private fun toDto(profile: Profile): ProfileDTO {
        return ProfileDTO(
            profileId = profile.id,
            nickname = profile.nickname,
            description = profile.description,
            socialMedia = profile.socialMedia
        )
    }

    override fun getByNickname(nickname: String): Profile {
        return profileRepository.findByNickname(nickname)
            .orElseThrow { NoSuchElementException() }
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(ProfileServiceImpl::class.java)
    }
}