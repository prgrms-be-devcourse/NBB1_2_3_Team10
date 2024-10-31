package org.tenten.bittakotlin.scout.controller.advice

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.tenten.bittakotlin.scout.exception.ScoutTaskException

@RestControllerAdvice
class ScoutControllerAdvice {
    @ExceptionHandler(ScoutTaskException::class)
    fun handleScoutException(e: ScoutTaskException): ResponseEntity<Map<String, String?>> {
        return ResponseEntity.status(e.code)
            .body(mapOf("error" to e.message))
    }
}