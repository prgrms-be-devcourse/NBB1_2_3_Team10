package org.tenten.bittakotlin.media.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.tenten.bittakotlin.media.dto.MediaRequestDto
import org.tenten.bittakotlin.media.service.MediaService

@RestController
@RequestMapping("/api/v1/media")
class MediaController(
    private val mediaService: MediaService
) {
    @GetMapping("/upload")
    fun upload(@RequestBody requestDto: MediaRequestDto.Upload): ResponseEntity<Map<String, Any>> {
        return ResponseEntity.ok(mapOf(
            "message" to "파일 업로드 링크를 성공적으로 생성했습니다.",
            "url" to mediaService.upload(requestDto)
        ))
    }

    @GetMapping("/read")
    fun read(@RequestBody requestDto: MediaRequestDto.Read): ResponseEntity<Map<String, Any>> {
        return ResponseEntity.ok(mapOf(
            "message" to "파일 조회 링크를 성공적으로 생성했습니다.",
            "url" to mediaService.read(requestDto)
        ))
    }
}