package org.tenten.bittakotlin.feedInteraction.viewCount.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.tenten.bittakotlin.feedInteraction.viewCount.dto.ViewCountDTO
import org.tenten.bittakotlin.feedInteraction.viewCount.service.ViewCountService


@RestController
@RequestMapping("/api/v1/feed/view")
class ViewCountController(private val viewCountService: ViewCountService) {
    @PostMapping("/{feedId}")
    fun addView(@PathVariable feedId: Long): ResponseEntity<ViewCountDTO> {
        val viewCountDTO = viewCountService.addView(feedId)
        return ResponseEntity.ok(viewCountDTO)
    }

    @GetMapping("/{feedId}/count")
    fun getViewCount(@PathVariable feedId: Long): ResponseEntity<ViewCountDTO> {
        val viewCountDTO = viewCountService.getViewCount(feedId)
        return ResponseEntity.ok(viewCountDTO)
    }
}