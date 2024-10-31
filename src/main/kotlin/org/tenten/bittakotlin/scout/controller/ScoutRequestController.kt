package org.tenten.bittakotlin.scout.controller
//테스트용 커밋입니다

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.tenten.bittakotlin.scout.dto.ScoutDTO
import org.tenten.bittakotlin.scout.service.ScoutRequestService

@RestController
@RequestMapping("/api/v1/scout")
class ScoutRequestController(
    private val scoutRequestService: ScoutRequestService
) {

    @PostMapping
    fun sendScoutRequest(@RequestBody scoutDTO: ScoutDTO): ResponseEntity<Map<String, Any>> {
        val savedScoutDTO = scoutRequestService.sendScoutRequest(scoutDTO)
        return ResponseEntity.ok(mapOf("message" to "스카우트를 저장했습니다.", "result" to savedScoutDTO))
    }

    @GetMapping("/sender/{memberId}")
    fun getSendScoutRequests(
        @PathVariable memberId: Long,
        @RequestParam(required = false, defaultValue = "0") page: Int,
        @RequestParam(required = false, defaultValue = "10") size: Int
    ): ResponseEntity<Map<String, Any>> {
        val pageable: Pageable = PageRequest.of(page, size)
        val sentRequests: Page<ScoutDTO> = scoutRequestService.getSentScoutRequests(memberId, pageable)
        return ResponseEntity.ok(mapOf("message" to "보낸 스카우트를 조회했습니다.", "sentRequests" to sentRequests))
    }

    @GetMapping("/receiver/{memberId}")
    fun getReceiveScoutRequests(
        @PathVariable memberId: Long,
        @RequestParam(required = false, defaultValue = "0") page: Int,
        @RequestParam(required = false, defaultValue = "10") size: Int
    ): ResponseEntity<Map<String, Any>> {
        val pageable: Pageable = PageRequest.of(page, size)
        val receivedRequests: Page<ScoutDTO> = scoutRequestService.getReceivedScoutRequests(memberId, pageable)
        return ResponseEntity.ok(mapOf("message" to "받은 스카우트를 조회했습니다.", "receivedRequests" to receivedRequests))
    }
}