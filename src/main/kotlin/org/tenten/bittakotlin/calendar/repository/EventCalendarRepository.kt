package org.tenten.bittakotlin.calendar.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.tenten.bittakotlin.calendar.entity.EventCalendar

interface EventCalendarRepository : JpaRepository<EventCalendar, Long> {
    @Query("SELECT c FROM EventCalendar c WHERE c.profile.id = :profileId")
    fun findAllByProfileId(profileId: Long): List<EventCalendar>
}
