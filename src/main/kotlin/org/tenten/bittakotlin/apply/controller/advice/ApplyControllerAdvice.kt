package org.tenten.bittakotlin.apply.controller.advice

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.tenten.bittakotlin.apply.exception.ApplyTaskException

@RestControllerAdvice
class ApplyControllerAdvice {

    @ExceptionHandler(ApplyTaskException::class)
    fun handleApplyTaskException(e: ApplyTaskException): ResponseEntity<Map<String, String>> {
        return ResponseEntity.status(e.code)
            .body(mapOf("error" to e.message))
    }
}


