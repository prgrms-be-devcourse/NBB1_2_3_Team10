package org.tenten.bittakotlin.media.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.tenten.bittakotlin.media.exception.MediaException

@RestControllerAdvice
class MediaControllerAdvice {
    @ExceptionHandler(MediaException::class)
    fun handleMediaException(e: MediaException): ResponseEntity<Map<String, Any>> {
        return ResponseEntity.status(e.code).body(mapOf("error" to e.message))
    }
}