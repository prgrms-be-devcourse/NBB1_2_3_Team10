package org.tenten.bittakotlin.feed.controller

import org.tenten.bittakotlin.feed.service.FeedService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.tenten.bittakotlin.feed.dto.FeedRequestDto
import org.tenten.bittakotlin.security.service.PrincipalProvider

@RestController
@RequestMapping("/api/v1/feed")
class FeedController (
    private val feedService: FeedService
) {
    @GetMapping
    fun readAll(
        @RequestParam(required = false, defaultValue = "0", value = "page") page: Int,
        @RequestParam(required = false, defaultValue = "10", value = "size") size: Int,
        @RequestParam(required = false, value = "nickname") nickname: String?,
        @RequestParam(required = false, value = "title") title: String?
    ): ResponseEntity<Map<String, Any>> {
        val pageable: Pageable = PageRequest.of(page, size)

        return ResponseEntity.ok(mapOf(
            "message" to "피드 목록을 성공적으로 조회했습니다.",
            "result" to feedService.getAll(pageable, nickname, title)
        ))
    }

    @GetMapping("/random")
    fun readRandom(@RequestParam(required = false, defaultValue = "0", value = "page") size: Int
    ): ResponseEntity<Map<String, Any>> {
        return ResponseEntity.ok(mapOf(
            "message" to "피드 목록을 무작위로 조회했습니다.",
            "result" to feedService.getRandom(size)
        ))
    }

    @GetMapping("/{id}")
    fun read(@PathVariable("id") id: Long): ResponseEntity<Map<String, Any>> {
        return ResponseEntity.ok(mapOf(
            "message" to "피드를 성공적으로 조회했습니다.",
            "result" to feedService.get(id)
        ))
    }

    @PostMapping
    fun create(@RequestBody requestDto: FeedRequestDto.Create): ResponseEntity<Map<String, Any>> {
        return ResponseEntity.ok(mapOf(
            "message" to "피드를 성공적으로 등록했습니다.",
            "result" to feedService.save(requestDto)
        ))
    }

    @PutMapping
    fun modifyFeed(
        @PathVariable("id") id: Long, @RequestBody requestDto: FeedRequestDto.Modify):
            ResponseEntity<Map<String, Any>> {
        return ResponseEntity.ok(mapOf(
            "message" to "피드를 성공적으로 수정했습니다.",
            "result" to feedService.update(id, requestDto)
        ))
    }

    @DeleteMapping("/{id}")
    fun deleteFeed(@PathVariable id: Long): ResponseEntity<Map<String, Any>> {
        feedService.delete(id)

        return ResponseEntity.ok(mapOf(
            "message" to "피드를 성공적으로 삭제했습니다."
        ))
    }
}