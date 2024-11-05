package org.tenten.bittakotlin.apply.service

import org.tenten.bittakotlin.apply.dto.ApplyDTO
import org.tenten.bittakotlin.apply.entity.Apply
import org.tenten.bittakotlin.apply.entity.ApplyStatus
import org.tenten.bittakotlin.calendar.entity.EventCalendar
import org.tenten.bittakotlin.profile.entity.Profile

interface ApplyService {
    fun readAll(profile: Profile): List<ApplyDTO>?

    fun register(applyDTO: ApplyDTO): Map<String, Any>

    fun delete(id: Long)

    fun read(id: Long): ApplyDTO

    fun getApplyIntoJobPost(jobPostId: Long, profileId: Long): List<ApplyDTO>

    fun findById(id: Long): ApplyDTO

    fun getApplyCount(jobPostId: Long): Long

    fun applyStatusUpdate(applyId: Long, applyStatus: ApplyStatus, profileId: Long)

    fun setCalendar(apply: Apply?)

    fun getCalendar(profileId: Long?): List<EventCalendar?>?

    fun applyToCalendar(jobPostId: Long?, profileId: Long?)
}


