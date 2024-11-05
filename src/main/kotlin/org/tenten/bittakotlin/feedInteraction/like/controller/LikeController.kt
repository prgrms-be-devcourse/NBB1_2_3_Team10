package org.tenten.bittakotlin.feedInteraction.like.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.tenten.bittakotlin.feedInteraction.like.dto.LikeDTO
import org.tenten.bittakotlin.feedInteraction.like.service.LikeService


@RestController
@RequestMapping("/api/v1/feed/like")
class LikeController(private val likeService: LikeService) {

    @PostMapping("/{feedId}")
    fun toggleLike(@PathVariable feedId: Long, @RequestParam profileId: Long): ResponseEntity<LikeDTO> {
        val likeDTO = likeService.toggleLike(feedId, profileId)
        return ResponseEntity.ok(likeDTO)
    }

    @GetMapping("/{feedId}/count")
    fun getLikeCount(@PathVariable feedId: Long): ResponseEntity<Long> {
        val likeCount = likeService.getLikeCount(feedId)
        return ResponseEntity.ok(likeCount)
    }
}