package org.tenten.bittakotlin.calendar.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.tenten.bittakotlin.calendar.dto.EventCalendarDTO
import org.tenten.bittakotlin.calendar.service.EventCalendarService

@RestController
@RequestMapping("/api/v1/calendar")
class EventCalendarController(
    private val eventCalendarService: EventCalendarService
) {

    @GetMapping("/{profileId}")
    fun getCalendar(@PathVariable profileId: Long): ResponseEntity<List<EventCalendarDTO>> {
        val events = eventCalendarService.getEventCalendar(profileId)
        return ResponseEntity.ok(events)
    }
}
