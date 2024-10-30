package org.tenten.bittakotlin.security.controller

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import org.tenten.bittakotlin.security.service.ReissueService


@RestController
class ReissueController(private val reissueService: ReissueService) {

    @PostMapping("/api/member/reissue")
    fun reissue(request: HttpServletRequest, response: HttpServletResponse): ResponseEntity<*> {
        return reissueService.reissueTokens(request, response)
    }
}