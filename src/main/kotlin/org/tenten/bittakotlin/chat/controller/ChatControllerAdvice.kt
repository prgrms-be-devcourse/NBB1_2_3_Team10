package org.tenten.bittakotlin.chat.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.tenten.bittakotlin.media.exception.ChatException

@RestControllerAdvice
class ChatControllerAdvice {
    @ExceptionHandler(ChatException::class)
    fun handleMediaException(e: ChatException): ResponseEntity<Map<String, Any>> {
        return ResponseEntity.status(e.code).body(mapOf("error" to e.message))
    }
}