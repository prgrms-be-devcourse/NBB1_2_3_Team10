package org.tenten.bittakotlin.jobpost.service

import org.hibernate.query.sqm.tree.SqmNode.log
import org.springframework.data.domain.PageRequest
import org.tenten.bittakotlin.apply.repository.ApplyRepository
import org.tenten.bittakotlin.apply.util.ApplyProvider
import org.tenten.bittakotlin.jobpost.dto.JobPostDTO
import org.tenten.bittakotlin.jobpost.entity.JobPost
import org.tenten.bittakotlin.jobpost.exception.JobPostException
import org.tenten.bittakotlin.jobpost.repository.JobPostRepository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.tenten.bittakotlin.apply.dto.ApplyDTO
import org.tenten.bittakotlin.apply.entity.Apply
import org.tenten.bittakotlin.global.dto.PageRequestDTO
import org.tenten.bittakotlin.profile.repository.ProfileRepository
import org.tenten.bittakotlin.profile.service.ProfileProvider

@Service
class JobPostServiceImpl(
    private val jobPostRepository: JobPostRepository,
    private val profileProvider: ProfileProvider,
    private val applyProvider: ApplyProvider,
//    private val mediaService: MediaService,
    private val applyRepository: ApplyRepository,
    private val profileRepository: ProfileRepository
) : JobPostService {

    @Transactional
    override fun getList(pageRequestDTO: PageRequestDTO): Page<JobPostDTO> {
        val sort = Sort.by("id").descending()
        val pageable = pageRequestDTO.getPageable(sort)
        val jobPosts = jobPostRepository.getList(pageable)
        return jobPosts.map { entityToDto(it) }
    }

    @Transactional
    override fun register(jobPostDTO: JobPostDTO): JobPostDTO {
        return try {
            var jobPost = dtoToEntity(jobPostDTO)
            jobPost = jobPostRepository.save(jobPost)
            entityToDto(jobPost)
        } catch (e: Exception) {
            log.error(e.message)
            throw JobPostException.NOT_REGISTERED.get()
        }
    }

    @Transactional
    override fun read(jobPostId: Long): JobPostDTO {
        val jobPost = jobPostRepository.getJobPost(jobPostId)
        return jobPost.map { entityToDto(it) }.orElseThrow { JobPostException.NOT_REGISTERED.get() }
    }

    @Transactional
    override fun modify(jobPostDTO: JobPostDTO): JobPostDTO {
        val jobPost = jobPostRepository.findById(jobPostDTO.id!!)
            .orElseThrow { JobPostException.NOT_FOUND.get() }

        return try {
            jobPost.title = jobPostDTO.title!!
            jobPost.description = jobPostDTO.description!!
            jobPost.location = jobPostDTO.location!!
            jobPost.payStatus = jobPostDTO.payStatus
            jobPost.workCategory = jobPostDTO.workCategory

            jobPostRepository.save(jobPost)
            entityToDto(jobPost)
        } catch (e: Exception) {
            log.error(e.message)
            throw JobPostException.NOT_MODIFIED.get()
        }
    }

    @Transactional
    override fun removeJobPost(jobPostId: Long) {
        val jobPost = jobPostRepository.findById(jobPostId).orElseThrow { JobPostException.NOT_FOUND.get() }
        val applies = jobPost.apply
        applies?.let {
            if (it.isNotEmpty()) {
                it.forEach { apply -> apply.jobPost = null }
                applyRepository.deleteAllInBatch(it)
            }
        }
//        jobPost.media?.let {
//            mediaService.delete(it)
//            jobPost.media = null
//        }
        jobPostRepository.delete(jobPost)
    }

    override fun getJobPostByMember(memberId: Long, pageRequestDTO: PageRequestDTO): Page<JobPostDTO> {
        val pageable = PageRequest.of(pageRequestDTO.page, pageRequestDTO.size)
        val jobPostPage = jobPostRepository.findJobPostByMember(memberId, pageable)
        return jobPostPage.map { entityToDto(it) }
    }

    override fun searchJobPosts(keyword: String, pageRequestDTO: PageRequestDTO): Page<JobPostDTO> {
        val sort = Sort.by("id").descending()
        val pageable = pageRequestDTO.getPageable(sort)
        val jobPosts = jobPostRepository.searchByKeyword(keyword, pageable)
        return jobPosts.map { entityToDto(it) }
    }

    @Transactional
    override fun getSortByDay(pageRequestDTO: PageRequestDTO): Page<JobPostDTO> {
        val sort = Sort.by("restDate").ascending()
        val pageable = pageRequestDTO.getPageable(sort)
        val jobPosts = jobPostRepository.findSortedByRestDate(pageable)
        return jobPosts.map { entityToDto(it) }
    }

    @Transactional
    override fun getSortByApplyCount(pageRequestDTO: PageRequestDTO): Page<JobPostDTO> {
        val sort = Sort.by("applyCount").descending()
        val pageable = pageRequestDTO.getPageable(sort)
        val jobPosts = jobPostRepository.findSortedByApplyCountDesc(pageable)
        return jobPosts.map { entityToDto(it) }
    }

    override fun getSortByCreatedAt(pageRequestDTO: PageRequestDTO): Page<JobPostDTO> {
        val sort = Sort.by("createdAt").ascending()
        val pageable = pageRequestDTO.getPageable(sort)
        val jobPosts = jobPostRepository.findSortedByCreatedAt(pageable)
        return jobPosts.map { entityToDto(it) }
    }

    private fun entityToDto(apply: Apply): ApplyDTO {
        return ApplyDTO(
            id = apply.id,
            profileId = apply.profile?.id,
            jobPostId = apply.jobPost?.id,
            appliedAt = apply.appliedAt
        )
    }

    private fun entityToDto(jobPost: JobPost): JobPostDTO {
        return JobPostDTO(
            id = jobPost.id,
            title = jobPost.title,
            description = jobPost.description,
            location = jobPost.location,
            payStatus = jobPost.payStatus,
            closeDate = jobPost.closeDate,
            workCategory = jobPost.workCategory,
            auditionDate = jobPost.auditionDate,
            startDate = jobPost.startDate,
            endDate = jobPost.endDate,
            updateAt = jobPost.updatedAt,
            restDate = jobPost.restDate,
            profileId = jobPost.profile?.id
        )
    }

    private fun dtoToEntity(jobPostDTO: JobPostDTO): JobPost {
        return JobPost(
            id = jobPostDTO.id,
            title = jobPostDTO.title!!,
            description = jobPostDTO.description!!,
            location = jobPostDTO.location!!,
            payStatus = jobPostDTO.payStatus,
            closeDate = jobPostDTO.closeDate,
            workCategory = jobPostDTO.workCategory,
            auditionDate = jobPostDTO.auditionDate,
            startDate = jobPostDTO.startDate,
            endDate = jobPostDTO.endDate,
            updatedAt = jobPostDTO.updateAt,
            restDate = jobPostDTO.restDate,
            profile = profileProvider.getById(jobPostDTO.profileId!!)!!,
            apply = applyProvider.getAllByJobPost(jobPostDTO.id!!)!!
        )
    }
}



