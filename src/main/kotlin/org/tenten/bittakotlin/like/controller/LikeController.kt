package org.tenten.bittakotlin.like.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.apache.logging.log4j.Logger
import org.apache.logging.log4j.LogManager
import org.tenten.bittakotlin.like.service.LikeService

@Tag(name = "좋아요 API 컨트롤러", description = "좋아요 기능과 관련된 REST API를 제공하는 컨트롤러입니다")
@RestController
@RequestMapping("/api/v1/like")
class LikeController (
    private val likeService: LikeService
) {
    private val log: Logger = LogManager.getLogger(LikeController::class.java)

    @Operation(
        summary = "좋아요 등록",
        description = "좋아요를 등록합니다.",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "좋아요를 성공적으로 등록했습니다.",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(example = LIKE_SUCCESS_REGISTER)
                )]
            ),
            ApiResponse(
                responseCode = "400",
                description = "좋아요 등록에 실패했습니다.",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(example = LIKE_FAILURE_NOT_REGISTERED)
                )]
            )
        ]
    )
    @PostMapping("/jobPost/{jobPostId}/member/{profileId}")
    fun addLike(@PathVariable jobPostId: Long, @PathVariable profileId: Long): ResponseEntity<String> {
        likeService.addLike(jobPostId, profileId)
        return ResponseEntity.ok("좋아요를 눌렀습니다")
    }

    @Operation(
        summary = "좋아요 삭제",
        description = "ID와 일치하는 좋아요를 취소합니다.",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "좋아요를 취소했습니다.",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(example = LIKE_SUCCESS_DELETE)
                )]
            ),
            ApiResponse(
                responseCode = "400",
                description = "좋아요 취소에 실패했습니다.",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(example = LIKE_FAILURE_NOT_REMOVED)
                )]
            ),
            ApiResponse(
                responseCode = "404",
                description = "취소할 좋아요가 존재하지 않습니다.",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(example = LIKE_FAILURE_NOT_FOUND)
                )]
            )
        ]
    )
    @DeleteMapping("/jobPost/{jobPostId}/member/{profileId}")
    fun removeLike(@PathVariable jobPostId: Long, @PathVariable profileId: Long): ResponseEntity<String> {
        likeService.removeLike(jobPostId, profileId)
        return ResponseEntity.ok("좋아요를 취소했습니다")
    }

    @Operation(
        summary = "좋아요를 누른 회원 조회",
        description = "해당 게시물에 좋아요를 누른 회원을 조회합니다.",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "좋아요를 누른 회원을 성공적으로 조회했습니다.",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(example = LIKE_SUCCESS_READ_ALL)
                )]
            ),
            ApiResponse(
                responseCode = "404",
                description = "좋아요가 존재하지 않습니다.",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(example = LIKE_FAILURE_NOT_FOUND)
                )]
            )
        ]
    )
    @GetMapping("/jobPost/{jobPostId}")
    fun getLikes(@PathVariable jobPostId: Long): ResponseEntity<List<Profile>> {
        val members = likeService.getLikesForJobPost(jobPostId)
        return ResponseEntity.ok(members)
    }
}