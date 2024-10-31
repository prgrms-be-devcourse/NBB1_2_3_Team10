package org.tenten.bittakotlin.security.service

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity

interface ReissueService {
    fun reissueTokens(request: HttpServletRequest, response: HttpServletResponse): ResponseEntity<*>
}