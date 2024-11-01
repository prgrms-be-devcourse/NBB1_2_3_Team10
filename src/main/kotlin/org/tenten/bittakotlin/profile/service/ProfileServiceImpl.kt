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
    private val memberRepository: MemberRepository
) : ProfileService {

    //Member 생성시 Profile 도 같이 생성
    @Transactional
    override fun createDefaultProfile(member: Member): ProfileDTO {

        val profile = Profile(
            member = member,
            nickname = member.nickname,
            profileUrl = null,
            description = "This is a default profile.",
            job = null,
            socialMedia = null
        )
        val savedProfile = profileRepository.save(profile)
        return toDto(savedProfile)
    }


    //자체적으로 profile 을 생성할경우. "테스트 용도"
    @Transactional
    override fun createProfile(profileDTO: ProfileDTO): ProfileDTO {
        val member = memberRepository.findById(profileDTO.memberId).orElseThrow {
            EntityNotFoundException("Member not found for memberId=${profileDTO.memberId}")
        }

        val profile = Profile(
            member = member,
            nickname = profileDTO.nickname,
            profileUrl = profileDTO.profileUrl,
            description = profileDTO.description,
            job = profileDTO.job?.let { Job.valueOf(it) },
            socialMedia = profileDTO.socialMedia
        )

        val savedProfile = profileRepository.save(profile)
        logger.info("Profile created successfully for memberId=${profileDTO.memberId}")

        return toDto(savedProfile)
    }


    @Transactional(readOnly = true)
    override fun getProfile(memberId: Long): ProfileDTO {
        logger.info("Fetching profile for memberId=$memberId")

        val profile = profileRepository.findByMemberId(memberId)
            ?: throw EntityNotFoundException("Profile not found for memberId=$memberId")

        logger.info("Profile fetched successfully for memberId=$memberId")
        return toDto(profile)
    }

    @Transactional
    override fun updateProfile(memberId: Long, profileDTO: ProfileDTO): ProfileDTO {
        logger.info("Updating profile for memberId=$memberId")

        val profile = profileRepository.findByMemberId(memberId)
            ?: throw EntityNotFoundException("Profile not found for memberId=$memberId")

        profile.nickname = profileDTO.nickname
        profile.profileUrl = profileDTO.profileUrl
        profile.description = profileDTO.description
        profile.job = profileDTO.job?.let { Job.valueOf(it) }
        profile.socialMedia = profileDTO.socialMedia

        val updatedProfile = profileRepository.save(profile)
        logger.info("Profile updated successfully for memberId=$memberId")

        return toDto(updatedProfile)
    }

    private fun toDto(profile: Profile): ProfileDTO {
        return ProfileDTO(
            memberId = profile.member.id ?: throw IllegalStateException("Member ID is missing"),
            nickname = profile.nickname,
            profileUrl = profile.profileUrl,
            description = profile.description,
            job = profile.job?.name,
            socialMedia = profile.socialMedia
        )
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(ProfileServiceImpl::class.java)
    }
}