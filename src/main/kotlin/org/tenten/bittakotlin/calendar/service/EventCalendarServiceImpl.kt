package org.tenten.bittakotlin.calendar.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.tenten.bittakotlin.calendar.dto.EventCalendarDTO
import org.tenten.bittakotlin.calendar.entity.EventCalendar
import org.tenten.bittakotlin.calendar.repository.EventCalendarRepository

@Service
class EventCalendarServiceImpl(
    private val eventCalendarRepository: EventCalendarRepository
) : EventCalendarService {

    private val logger = LoggerFactory.getLogger(EventCalendarServiceImpl::class.java)

    override fun getEventCalendar(profileId: Long): List<EventCalendarDTO> {
        val events = eventCalendarRepository.findAllByProfileId(profileId)
        return events.map { entityToDto(it) }
    }

    private fun entityToDto(event: EventCalendar): EventCalendarDTO {
        return EventCalendarDTO(
            id = event.id,
            profileId = event.profile!!.id,
            startDate = event.startDate,
            endDate = event.endDate,
            title = event.title,
            auditionDate = event.auditionDate
        )
    }
}
