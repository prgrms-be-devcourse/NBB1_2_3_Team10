package org.tenten.bittakotlin.feedInteraction.feedLike.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.tenten.bittakotlin.feedInteraction.feedLike.dto.FeedLikeDTO
import org.tenten.bittakotlin.feedInteraction.feedLike.service.FeedLikeService


@RestController
@RequestMapping("/api/v1/feed/like")
class FeedLikeController(private val likeService: FeedLikeService) {

    @PostMapping("/{feedId}")
    fun toggleLike(@PathVariable feedId: Long, @RequestParam profileId: Long): ResponseEntity<FeedLikeDTO> {
        val likeDTO = likeService.toggleLike(feedId, profileId)
        return ResponseEntity.ok(likeDTO)
    }

    @GetMapping("/{feedId}/count")
    fun getLikeCount(@PathVariable feedId: Long): ResponseEntity<Long> {
        val likeCount = likeService.getLikeCount(feedId)
        return ResponseEntity.ok(likeCount)
    }
}