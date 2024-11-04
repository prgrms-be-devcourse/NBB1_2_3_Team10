package org.tenten.bittakotlin.feed.controller

import org.tenten.bittakotlin.feed.dto.FeedDTO
import org.tenten.bittakotlin.feed.dto.FeedRequestDto.Modify
import org.tenten.bittakotlin.feed.service.FeedService
import org.tenten.bittakotlin.global.constants.ApiResponses.*
import org.tenten.bittakotlin.global.exception.AuthenticationException
import org.tenten.bittakotlin.global.util.AuthenticationProvider
import org.tenten.bittakotlin.member.entity.Role
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.Parameters
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import lombok.RequiredArgsConstructor
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.Map

@Tag(name = "피드 API 컨트롤러", description = "피드와 관련된 REST API를 제공하는 컨틀롤러입니다.")
@RestController
@RequestMapping("/api/v1/feed")
@RequiredArgsConstructor
@Validated
class FeedController {
    private val feedService: FeedService? = null


    @GetMapping
    fun getFeeds(
        @RequestParam(required = false, defaultValue = "0", value = "page") page: Int,
        @RequestParam(required = false, defaultValue = "10", value = "size") size: Int,
        @RequestParam(required = false, value = "username") username: String?,
        @RequestParam(required = false, value = "title") title: String?
    ): ResponseEntity<*> {
        val pageable: Pageable = PageRequest.of(page, size)

        return ResponseEntity.ok<T>(
            Map.of<K, V>(
                "message", "피드를 성공적으로 조회했습니다.",
                "result", feedService.readAll(pageable, username, title)
            )
        )
    }

    @GetMapping("/{id}")
    fun getFeedById(@PathVariable("id") id: @Min(1) Long?): ResponseEntity<*> {
        return ResponseEntity.ok<T>(
            Map.of<K, V>("message", "피드를 성공적으로 조회했습니다.", "result", feedService.read(id))
        )
    }

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE])
    fun createFeed(
        @RequestPart(value = "feed") feedDto: @Valid FeedDTO?,
        @RequestPart(value = "files", required = false) files: List<MultipartFile?>?
    ): ResponseEntity<*> {
        feedService.insert(feedDto, files)

        return ResponseEntity.ok().body(Map.of("message", "피드가 등록되었습니다."))
    }

    @PutMapping(value = ["/{id}"], consumes = [MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE])
    fun modifyFeed(
        @PathVariable("id") id: @Min(1) Long?,
        @RequestPart("feed") feedDTO: @Valid Modify,
        @RequestPart("filesToUpload") filesToUpload: List<MultipartFile?>?,
        @RequestPart("filesToDelete") filesToDelete: List<String?>?
    ): ResponseEntity<*> {
        if (!checkPermission(id)) {
            throw AuthenticationException.CANNOT_ACCESS.get()
        }

        feedDTO.setId(id)

        feedService.update(feedDTO, filesToUpload, filesToDelete)

        return ResponseEntity.ok().body(Map.of("message", "피드가 수정되었습니다."))
    }

    @DeleteMapping("/{id}")
    fun deleteFeed(@PathVariable("id") id: @Min(1) Long?): ResponseEntity<*> {
        if (!checkPermission(id)) {
            throw AuthenticationException.CANNOT_ACCESS.get()
        }

        feedService.delete(id)

        return ResponseEntity.ok().body(Map.of("message", "피드가 삭제되었습니다."))
    }

    private fun checkPermission(id: Long?): Boolean {
        if (AuthenticationProvider.getRoles() === Role.ROLE_ADMIN) {
            return true
        }

        return feedService.checkAuthority(id, AuthenticationProvider.getUsername())
    }
}