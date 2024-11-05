package org.tenten.bittakotlin.calendar.service

import org.tenten.bittakotlin.calendar.dto.EventCalendarDTO

interface EventCalendarService {
    fun getEventCalendar(profileId: Long): List<EventCalendarDTO>
}
