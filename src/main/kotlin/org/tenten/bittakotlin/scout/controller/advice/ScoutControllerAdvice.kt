package org.tenten.bittakotlin.scout.controller.advice

import com.prgrms2.java.bitta.scout.exception.ScoutTaskException
import org.springframework.web.bind.annotation.ExceptionHandler
import java.util.Map

@RestControllerAdvice
class ScoutControllerAdvice {
    @ExceptionHandler(ScoutTaskException::class)
    fun handleScoutException(e: ScoutTaskException): ResponseEntity<*> {
        return ResponseEntity.status(e.getCode())
            .body(Map.of("error", e.getMessage()))
    }
}