package org.tenten.bittakotlin.jobpost.service

import org.springframework.data.domain.Page
import org.tenten.bittakotlin.global.dto.PageRequestDTO
import org.tenten.bittakotlin.jobpost.dto.JobPostDTO

interface JobPostService {

    fun register(jobPostDTO: JobPostDTO): JobPostDTO

    fun read(id: Long): JobPostDTO

    fun modify(jobPostDTO: JobPostDTO): JobPostDTO

    fun getList(pageRequestDTO: PageRequestDTO): Page<JobPostDTO>

    fun removeJobPost(jobPostId: Long)

    fun getJobPostByMember(memberId: Long, pageRequestDTO: PageRequestDTO): Page<JobPostDTO>

    fun searchJobPosts(keyword: String, pageRequestDTO: PageRequestDTO): Page<JobPostDTO>

    fun getSortByDay(pageRequestDTO: PageRequestDTO): Page<JobPostDTO>

    fun getSortByApplyCount(pageRequestDTO: PageRequestDTO): Page<JobPostDTO>

    fun getSortByCreatedAt(pageRequestDTO: PageRequestDTO): Page<JobPostDTO>
}

