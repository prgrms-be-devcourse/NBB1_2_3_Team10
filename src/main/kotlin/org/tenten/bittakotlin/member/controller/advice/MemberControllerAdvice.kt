package org.tenten.bittakotlin.member.controller.advice

import org.springframework.http.ResponseEntity
import org.springframework.web.ErrorResponse
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.tenten.bittakotlin.member.exception.MemberTaskException

@RestControllerAdvice
class MemberControllerAdvice {

    @ExceptionHandler(MemberTaskException::class)
    fun handleMemberTaskException(e: MemberTaskException): ResponseEntity<Map<String, String>> {
        return ResponseEntity.status(e.code)
            .body(mapOf("error" to e.message))
    }
}