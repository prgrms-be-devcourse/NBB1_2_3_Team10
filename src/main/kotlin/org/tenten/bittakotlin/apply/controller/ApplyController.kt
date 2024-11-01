package org.tenten.bittakotlin.apply.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.tenten.bittakotlin.apply.dto.ApplyDTO
import org.tenten.bittakotlin.apply.dto.ApplyStatusUpdateDTO
import org.tenten.bittakotlin.apply.service.ApplyService

@Tag(name = "지원서 API 컨트롤러", description = "지원서와 관련된 REST API를 제공하는 컨트롤러입니다.")
@RestController
@RequestMapping("api/v1/apply")
@RequiredArgsConstructor
class ApplyController(
    private val applyService: ApplyService
) {

//    @Operation(
//        summary = "전체 지원서 조회",
//        description = "회원의 전체 지원서를 조회합니다.",
//        responses = [
//            ApiResponse(
//                responseCode = "200",
//                description = "지원서를 성공적으로 조회했습니다.",
//                content = [Content(
//                    mediaType = "application/json",
//                    schema = Schema(example = APPLY_SUCCESS_READ_ALL)
//                )]
//            )
//        ]
//    )
    @GetMapping
    fun findAll(profile: Profile): ResponseEntity<List<ApplyDTO>> {
        return ResponseEntity.ok(applyService.readAll(profile))
    }

//    @Operation(
//        summary = "지원서 등록",
//        description = "지원서를 등록합니다.",
//        responses = [
//            ApiResponse(
//                responseCode = "200",
//                description = "지원서를 성공적으로 등록했습니다.",
//                content = [Content(
//                    mediaType = "application/json",
//                    schema = Schema(example = APPLY_SUCCESS_REGISTER)
//                )]
//            ),
//            ApiResponse(
//                responseCode = "400",
//                description = "지원서 등록에 실패했습니다.",
//                content = [Content(
//                    mediaType = "application/json",
//                    schema = Schema(example = APPLY_FAILURE_NOT_REGISTERED)
//                )]
//            )
//        ]
//    )
    @PostMapping
    fun registerApply(@Valid @RequestBody applyDTO: ApplyDTO): ResponseEntity<Map<String, Any>> {
        return ResponseEntity.ok(applyService.register(applyDTO))
    }

//    @Operation(
//        summary = "단일 지원서 조회",
//        description = "단일 지원서를 조회합니다.",
//        responses = [
//            ApiResponse(
//                responseCode = "200",
//                description = "지원서를 조회했습니다.",
//                content = [Content(
//                    mediaType = "application/json",
//                    schema = Schema(example = APPLY_SUCCESS_READ)
//                )]
//            ),
//            ApiResponse(
//                responseCode = "400",
//                description = "지원서가 존재하지 않습니다.",
//                content = [Content(
//                    mediaType = "application/json",
//                    schema = Schema(example = APPLY_FAILURE_NOT_FOUND)
//                )]
//            )
//        ]
//    )
//    @Parameter(
//        name = "id",
//        description = "조회할 지원서의 ID",
//        required = true,
//        example = "1",
//        schema = Schema(type = "integer")
//    )
    @GetMapping("/{id}")
    fun readApply(@PathVariable("id") id: Long): ResponseEntity<ApplyDTO> {
        val applyDTO = applyService.findById(id)
        return ResponseEntity.ok(applyDTO)
    }

//    @Operation(
//        summary = "지원서 삭제",
//        description = "지원서를 삭제합니다.",
//        responses = [
//            ApiResponse(
//                responseCode = "200",
//                description = "지원서가 삭제되었습니다.",
//                content = [Content(
//                    mediaType = "application/json",
//                    schema = Schema(example = APPLY_SUCCESS_DELETE)
//                )]
//            ),
//            ApiResponse(
//                responseCode = "400",
//                description = "지원서 삭제에 실패했습니다.",
//                content = [Content(
//                    mediaType = "application/json",
//                    schema = Schema(example = APPLY_FAILURE_NOT_REMOVED)
//                )]
//            ),
//            ApiResponse(
//                responseCode = "404",
//                description = "삭제할 지원서가 존재하지 않습니다.",
//                content = [Content(
//                    mediaType = "application/json",
//                    schema = Schema(example = APPLY_FAILURE_NOT_FOUND)
//                )]
//            )
//        ]
//    )
//    @Parameter(
//        name = "id",
//        description = "삭제할 지원서의 ID",
//        required = true,
//        example = "1",
//        schema = Schema(type = "integer")
//    )
    @DeleteMapping("/{id}")
    fun deleteApply(@Valid @PathVariable("id") id: Long): ResponseEntity<Map<String, String>> {
        applyService.delete(id)
        return ResponseEntity.ok(mapOf("message" to "삭제가 완료되었습니다"))
    }

//    @Operation(
//        summary = "게시물의 지원서 조회",
//        description = "게시물에 해당하는 지원서를 조회합니다.",
//        responses = [
//            ApiResponse(
//                responseCode = "200",
//                description = "지원서를 성공적으로 조회했습니다.",
//                content = [Content(
//                    mediaType = "application/json",
//                    schema = Schema(example = APPLY_SUCCESS_READ_ALL)
//                )]
//            ),
//            ApiResponse(
//                responseCode = "404",
//                description = "지원서가 존재하지 않습니다.",
//                content = [Content(
//                    mediaType = "application/json",
//                    schema = Schema(example = APPLY_FAILURE_NOT_FOUND)
//                )]
//            )
//        ]
//    )
//    @Parameter(
//        name = "jobPostId, memberId",
//        description = "지원서의 해당 일거리 ID, 작성자의 회원 ID",
//        required = true,
//        example = "1, 1",
//        schema = Schema(type = "integer")
//    )
    @GetMapping("/job-post/{jobPostId}/member/{profileId}")
    fun getApplyIntoJobPost(
        @PathVariable jobPostId: Long,
        @PathVariable profileId: Long
    ): ResponseEntity<List<ApplyDTO>> {
        val applies = applyService.getApplyIntoJobPost(jobPostId, profileId)
        return ResponseEntity.ok(applies)
    }

//    @Operation(
//        summary = "지원 검토 상태 수정",
//        description = "게시물에 해당하는 지원의 검토 상태를 수정합니다.",
//        responses = [
//            ApiResponse(
//                responseCode = "200",
//                description = "검토 상태를 성공적으로 수정했습니다.",
//                content = [Content(
//                    mediaType = "application/json",
//                    schema = Schema(example = APPLY_SUCCESS_MODIFY)
//                )]
//            ),
//            ApiResponse(
//                responseCode = "400",
//                description = "검토 상태 수정에 실패했습니다.",
//                content = [Content(
//                    mediaType = "application/json",
//                    schema = Schema(example = APPLY_FAILURE_NOT_MODIFY)
//                )]
//            ),
//            ApiResponse(
//                responseCode = "404",
//                description = "일거리가 존재하지 않습니다.",
//                content = [Content(
//                    mediaType = "application/json",
//                    schema = Schema(example = APPLY_FAILURE_NOT_FOUND)
//                )]
//            )
//        ]
//    )
//    @Parameter(
//        name = "applyId, profileId",
//        description = "해당 게시물의 지원 ID, 게시자의 프로필 ID",
//        required = true,
//        example = "1, 1",
//        schema = Schema(type = "integer")
//    )
    @PutMapping("/{applyId}/status/{profileId}")
    fun applyStatusUpdate(
        @PathVariable applyId: Long,
        @PathVariable profileId: Long,
        @RequestBody applyStatusUpdateDTO: ApplyStatusUpdateDTO
    ): ResponseEntity<String> {
        applyService.applyStatusUpdate(applyId, applyStatusUpdateDTO.applyStatus, profileId)
        return ResponseEntity.ok("상태가 변경되었습니다")
    }
}

