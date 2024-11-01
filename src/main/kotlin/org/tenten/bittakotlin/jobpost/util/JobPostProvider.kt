package org.tenten.bittakotlin.jobpost.util

import org.tenten.bittakotlin.jobpost.entity.JobPost
import org.tenten.bittakotlin.jobpost.repository.JobPostRepository

import org.springframework.stereotype.Component

@Component
class JobPostProvider(
    private val jobPostRepository: JobPostRepository
) {

    fun getById(id: Long): JobPost? {
        return jobPostRepository.findById(id).orElse(null)
    }
}
