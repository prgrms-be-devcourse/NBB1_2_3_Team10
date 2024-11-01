package org.tenten.bittakotlin.apply.util

import lombok.RequiredArgsConstructor
import org.tenten.bittakotlin.apply.entity.Apply
import org.tenten.bittakotlin.apply.repository.ApplyRepository

import org.springframework.stereotype.Component

@Component
@RequiredArgsConstructor
class ApplyProvider(
    private val applyRepository: ApplyRepository
) {

    fun getAllByJobPost(jobPostId: Long): List<Apply>? {
        val applies = applyRepository.findAllByJobPostId(jobPostId)
        return applies.ifEmpty { null }
    }
}

