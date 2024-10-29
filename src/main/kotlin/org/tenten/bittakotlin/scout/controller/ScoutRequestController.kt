package org.tenten.bittakotlin.scout.controller

import com.prgrms2.java.bitta.scout.dto.ScoutDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

@RestController
@RequestMapping("/api/v1/scout")
@RequiredArgsConstructor
class ScoutRequestController {
    private val scoutRequestService: ScoutRequestService? = null

    @PostMapping
    fun sendScoutRequest(@RequestBody scoutDTO: ScoutDTO?): ResponseEntity<*> {
        var scoutDTO: ScoutDTO? = scoutDTO
        scoutDTO = scoutRequestService.sendScoutRequest(scoutDTO)
        return ResponseEntity.ok().body<Map<String, String>>(
            java.util.Map.of<String, String?>(
                "message",
                "스카우트를 저장했습니다.",
                "result",
                scoutDTO
            )
        )
    }

    @GetMapping("/sender/{memberId}")
    fun getSendScoutRequests(
        @PathVariable memberId: Long?,
        @RequestParam(required = false, defaultValue = "0", value = "page") page: Int,
        @RequestParam(required = false, defaultValue = "10", value = "size") size: Int
    ): ResponseEntity<*> {
        val pageable: Pageable = PageRequest.of(page, size)

        val sentRequests: Page<ScoutDTO> = scoutRequestService.getSentScoutRequests(memberId, pageable)

        return ResponseEntity.ok().body<Map<String, Any>>(
            java.util.Map.of<String, Any>(
                "message", "보낸 스카우트를 조회했습니다.",
                "receivedRequests", sentRequests
            )
        )
    }

    @GetMapping("/receiver/{memberId}")
    fun getReceiveScoutRequests(
        @PathVariable memberId: Long?,
        @RequestParam(required = false, defaultValue = "0", value = "page") page: Int,
        @RequestParam(required = false, defaultValue = "10", value = "size") size: Int
    ): ResponseEntity<*> {
        val pageable: Pageable = PageRequest.of(page, size)

        val receivedRequests: Page<ScoutDTO> = scoutRequestService.getReceivedScoutRequests(memberId, pageable)

        return ResponseEntity.ok().body<Map<String, Any>>(
            java.util.Map.of<String, Any>(
                "message", "받은 스카우트를 조회했습니다.",
                "receivedRequests", receivedRequests
            )
        )
    }
}