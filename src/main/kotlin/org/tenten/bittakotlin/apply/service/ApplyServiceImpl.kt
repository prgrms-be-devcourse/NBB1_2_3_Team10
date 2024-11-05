package org.tenten.bittakotlin.apply.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.tenten.bittakotlin.apply.dto.ApplyDTO
import org.tenten.bittakotlin.apply.entity.Apply
import org.tenten.bittakotlin.apply.entity.ApplyStatus
import org.tenten.bittakotlin.apply.exception.ApplyException
import org.tenten.bittakotlin.apply.repository.ApplyRepository
import org.tenten.bittakotlin.calendar.entity.EventCalendar
import org.tenten.bittakotlin.calendar.repository.EventCalendarRepository
import org.tenten.bittakotlin.jobpost.exception.JobPostException
import org.tenten.bittakotlin.jobpost.repository.JobPostRepository
import org.tenten.bittakotlin.jobpost.util.JobPostProvider
import org.tenten.bittakotlin.profile.entity.Profile
import org.tenten.bittakotlin.profile.service.ProfileProvider

@Service
class ApplyServiceImpl(
    private val applyRepository: ApplyRepository,
    private val jobPostRepository: JobPostRepository,
    private val profileProvider: ProfileProvider,
    private val jobPostProvider: JobPostProvider,
    private val eventCalendarRepository: EventCalendarRepository
) : ApplyService {

    private val log = LoggerFactory.getLogger(this::class.java)

    @Transactional
    override fun readAll(profile: Profile): List<ApplyDTO>? {
        val applies = applyRepository.findAllByMember(profile)
        return if (applies.isEmpty()) null else applies.map { entityToDto(it) }
    }

    @Transactional
    override fun register(applyDTO: ApplyDTO): Map<String, Any> {
        return try {
            var apply = dtoToEntity(applyDTO)
            apply = applyRepository.save(apply)

            val jobPost = apply.jobPost
            jobPost!!.plusApplyCount()
            jobPostRepository.save(jobPost)

            addCalendar(apply)

            mapOf(
                "message" to "${apply.profile!!.nickname}님 지원 완료",
                "data" to entityToDto(apply)
            )
        } catch (e: Exception) {
            log.error(e.message)
            throw ApplyException.NOT_REGISTERED.get()
        }
    }

    private fun addCalendar(apply: Apply) {
        val jobPost = apply.jobPost
        val event = EventCalendar(
            profile = apply.profile,
            title = jobPost!!.title,
            startDate = jobPost.startDate,
            endDate = jobPost.endDate,
            auditionDate = jobPost.auditionDate
        )
        eventCalendarRepository.save(event)
    }

    @Transactional
    override fun delete(id: Long) {
        val apply = applyRepository.findById(id).orElseThrow { ApplyException.NOT_FOUND.get() }

        val jobPost = apply.jobPost
        if (apply.profile != null) {
            apply.profile = null
        }
        if (apply.jobPost != null) {
            jobPost!!.minusApplyCount()
            jobPostRepository.save(jobPost)
            apply.jobPost = null
        }
        applyRepository.delete(apply)
    }

    @Transactional
    override fun read(id: Long): ApplyDTO {
        val applyDTO = applyRepository.getApplyDTO(id).map { entityToDto(it) }.orElseThrow{ApplyException.NOT_FOUND.get()}
        return applyDTO
    }

    override fun getApplyIntoJobPost(jobPostId: Long, profileId: Long): List<ApplyDTO> {
        val jobPost = jobPostRepository.findById(jobPostId).orElseThrow { JobPostException.NOT_FOUND.get() }

        if (jobPost.profile!!.id != profileId) {
            throw JobPostException.BAD_REQUEST.get()
        }

        val applies = applyRepository.findAllByJobPost(jobPost)
        return applies
    }

    @Transactional
    override fun findById(id: Long): ApplyDTO {
        val applyDTO = applyRepository.findById(id)
        return applyDTO.map { entityToDto(it) }.orElseThrow { ApplyException.NOT_FOUND.get() }
    }

    override fun getApplyCount(jobPostId: Long): Long {
        return applyRepository.countByJobPostId(jobPostId)
    }

    override fun applyStatusUpdate(applyId: Long, applyStatus: ApplyStatus, profileId: Long) {
        val apply = applyRepository.findById(applyId).orElseThrow { ApplyException.NOT_FOUND.get() }

        val jobPostOwnerId = apply.jobPost!!.profile!!.id
        if (jobPostOwnerId != profileId) {
            throw ApplyException.NOT_FOUND.get()
        }

        apply.status = applyStatus
        applyRepository.save(apply)
    }

    override fun setCalendar(apply: Apply?) {
        val event = EventCalendar(
            profile = apply!!.profile,
            title = apply.jobPost!!.title,
            startDate = apply.jobPost!!.startDate,
            endDate = apply.jobPost!!.endDate,
            auditionDate = apply.jobPost!!.auditionDate
        )
        eventCalendarRepository.save(event)
    }

    override fun getCalendar(profileId: Long?): List<EventCalendar?>? {
        return eventCalendarRepository.findAllByProfileId(profileId!!)
    }

    @Transactional
    override fun applyToCalendar(jobPostId: Long?, profileId: Long?) {
        val apply = Apply()
    }

    private fun dtoToEntity(applyDTO: ApplyDTO): Apply {
        return Apply(
            id = applyDTO.id,
            profile = profileProvider.getById(applyDTO.profileId!!),
            jobPost = jobPostProvider.getById(applyDTO.jobPostId!!),
            appliedAt = applyDTO.appliedAt
        )
    }

    private fun entityToDto(apply: Apply): ApplyDTO {
        val profileId = apply.profile?.id ?: throw ApplyException.NOT_FOUND.get()
        val jobPostId = apply.jobPost?.id ?: throw ApplyException.NOT_FOUND.get()

        return ApplyDTO(
            id = apply.id,
            profileId = profileId,
            jobPostId = jobPostId,
            appliedAt = apply.appliedAt
        )
    }
}

