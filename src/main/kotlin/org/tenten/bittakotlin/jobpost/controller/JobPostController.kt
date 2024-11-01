package org.tenten.bittakotlin.jobpost.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import lombok.RequiredArgsConstructor
import lombok.extern.log4j.Log4j2
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.tenten.bittakotlin.apply.repository.ApplyRepository
import org.tenten.bittakotlin.apply.service.ApplyService
import org.tenten.bittakotlin.global.dto.PageRequestDTO
import org.tenten.bittakotlin.jobpost.dto.JobPostDTO
import org.tenten.bittakotlin.jobpost.repository.JobPostRepository
import org.tenten.bittakotlin.jobpost.service.JobPostService

@Tag(name = "일거리 API 컨트롤러", description = "일거리와 관련된 REST API를 제공하는 컨트롤러입니다.")
@RestController
@RequestMapping("/api/v1/job-post")
@Log4j2
@RequiredArgsConstructor
class JobPostController(
    private val jobPostService: JobPostService,
    private val applyService: ApplyService,
    private val jobPostRepository: JobPostRepository,
    private val applyRepository: ApplyRepository
) {

//    @Operation(
//        summary = "전체 일거리 조회",
//        description = "전체 일거리를 조회합니다.",
//        responses = [
//            ApiResponse(
//                responseCode = "200",
//                description = "일거리를 성공적으로 조회했습니다.",
//                content = [Content(
//                    mediaType = "application/json",
//                    schema = Schema(example = JOB_POST_SUCCESS_READ_ALL)
//                )]
//            ),
//            ApiResponse(
//                responseCode = "404",
//                description = "일거리가 존재하지 않습니다.",
//                content = [Content(
//                    mediaType = "application/json",
//                    schema = Schema(example = JOB_POST_FAILURE_NOT_FOUND)
//                )]
//            )
//        ]
//    )
    @GetMapping
    fun getList(@Valid pageRequestDTO: PageRequestDTO): ResponseEntity<Page<JobPostDTO>> =
        ResponseEntity.ok(jobPostService.getList(pageRequestDTO))

//    @Operation(
//        summary = "일거리 등록",
//        description = "일거리를 등록합니다.",
//        responses = [
//            ApiResponse(
//                responseCode = "200",
//                description = "일거리를 성공적으로 등록했습니다.",
//                content = [Content(
//                    mediaType = "application/json",
//                    schema = Schema(example = JOB_POST_SUCCESS_REGISTER)
//                )]
//            ),
//            ApiResponse(
//                responseCode = "400",
//                description = "일거리 등록에 실패했습니다.",
//                content = [Content(
//                    mediaType = "application/json",
//                    schema = Schema(example = JOB_POST_FAILURE_NOT_REGISTERED)
//                )]
//            )
//        ]
//    )
    @PostMapping
    fun registerJobPost(@RequestBody jobPostDTO: JobPostDTO): ResponseEntity<JobPostDTO> =
        ResponseEntity.ok(jobPostService.register(jobPostDTO))

//    @Operation(
//        summary = "단일 일거리 조회",
//        description = "ID와 일치하는 일거리를 조회합니다.",
//        responses = [
//            ApiResponse(
//                responseCode = "200",
//                description = "일거리를 성공적으로 조회했습니다.",
//                content = [Content(
//                    mediaType = "application/json",
//                    schema = Schema(example = JOB_POST_SUCCESS_READ)
//                )]
//            ),
//            ApiResponse(
//                responseCode = "404",
//                description = "일거리가 존재하지 않습니다.",
//                content = [Content(
//                    mediaType = "application/json",
//                    schema = Schema(example = JOB_POST_FAILURE_NOT_FOUND)
//                )]
//            )
//        ]
//    )
//    @Parameter(
//        name = "id",
//        description = "조회할 일거리의 ID",
//        required = true,
//        example = "1",
//        schema = Schema(type = "integer")
//    )
    @GetMapping("/{id}")
    fun readJobPost(@PathVariable("id") @Valid id: Long): ResponseEntity<JobPostDTO> =
        ResponseEntity.ok(jobPostService.read(id))

//    @Operation(
//        summary = "일거리 수정",
//        description = "ID와 일치하는 일거리를 수정합니다.",
//        responses = [
//            ApiResponse(
//                responseCode = "200",
//                description = "일거리를 성공적으로 수정했습니다.",
//                content = [Content(
//                    mediaType = "application/json",
//                    schema = Schema(example = JOB_POST_SUCCESS_MODIFY)
//                )]
//            ),
//            ApiResponse(
//                responseCode = "400",
//                description = "일거리 수정에 실패했습니다.",
//                content = [Content(
//                    mediaType = "application/json",
//                    schema = Schema(example = JOB_POST_FAILURE_NOT_MODIFIED)
//                )]
//            ),
//            ApiResponse(
//                responseCode = "404",
//                description = "일거리가 존재하지 않습니다.",
//                content = [Content(
//                    mediaType = "application/json",
//                    schema = Schema(example = JOB_POST_FAILURE_NOT_FOUND)
//                )]
//            )
//        ]
//    )
//    @Parameter(
//        name = "id",
//        description = "수정할 일거리의 ID",
//        required = true,
//        example = "1",
//        schema = Schema(type = "integer")
//    )
    @PutMapping("/{id}")
    fun modifyJobPost(@RequestBody jobPostDTO: JobPostDTO, @Valid @PathVariable("id") id: Long): ResponseEntity<JobPostDTO> =
        ResponseEntity.ok(jobPostService.modify(jobPostDTO))

//    @Operation(
//        summary = "일거리 삭제",
//        description = "ID와 일치하는 일거리를 삭제합니다.",
//        responses = [
//            ApiResponse(
//                responseCode = "200",
//                description = "일거리를 삭제했습니다.",
//                content = [Content(
//                    mediaType = "application/json",
//                    schema = Schema(example = JOB_POST_SUCCESS_DELETE)
//                )]
//            ),
//            ApiResponse(
//                responseCode = "400",
//                description = "일거리 삭제에 실패했습니다.",
//                content = [Content(
//                    mediaType = "application/json",
//                    schema = Schema(example = JOB_POST_FAILURE_NOT_REMOVED)
//                )]
//            ),
//            ApiResponse(
//                responseCode = "404",
//                description = "삭제할 일거리가 존재하지 않습니다.",
//                content = [Content(
//                    mediaType = "application/json",
//                    schema = Schema(example = JOB_POST_FAILURE_NOT_FOUND)
//                )]
//            )
//        ]
//    )
//    @Parameter(
//        name = "id",
//        description = "삭제할 일거리의 ID",
//        required = true,
//        example = "1",
//        schema = Schema(type = "integer")
//    )
    @DeleteMapping("/{id}")
    fun deleteJobPost(@Valid @PathVariable("id") id: Long): ResponseEntity<Map<String, String>> {
        jobPostService.removeJobPost(id)
        return ResponseEntity.ok(mapOf("message" to "삭제가 완료되었습니다"))
    }

//    @Operation(
//        summary = "작성자의 다른 게시물 조회",
//        description = "작성자의 ID와 동일한 게시물을 조회합니다",
//        responses = [
//            ApiResponse(
//                responseCode = "200",
//                description = "작성자의 게시물 조회에 성공했습니다",
//                content = [Content(
//                    mediaType = "application/json",
//                    schema = Schema(example = JOB_POST_SUCCESS_READ)
//                )]
//            ),
//            ApiResponse(
//                responseCode = "404",
//                description = "작성자의 게시물을 찾을 수 없습니다.",
//                content = [Content(
//                    mediaType = "application/json",
//                    schema = Schema(example = JOB_POST_FAILURE_NOT_FOUND)
//                )]
//            )
//        ]
//    )
//    @Parameter(
//        name = "profileId",
//        description = "작성자의 ID",
//        required = true,
//        example = "1",
//        schema = Schema(type = "integer")
//    )
    @GetMapping("/member/{profileId}")
    fun getJobPostByMember(
        @PathVariable profileId: Long,
        @ModelAttribute pageRequestDTO: PageRequestDTO
    ): ResponseEntity<Page<JobPostDTO>> {
        val result = jobPostService.getJobPostByMember(profileId, pageRequestDTO)
        return ResponseEntity.ok(result)
    }

//    @Operation(
//        summary = "키워드 검색",
//        description = "키워드가 포함된 게시물을 검색합니다.",
//        responses = [
//            ApiResponse(
//                responseCode = "200",
//                description = "검색 결과를 성공적으로 조회했습니다.",
//                content = [Content(
//                    mediaType = "application/json",
//                    schema = Schema(example = JOB_POST_SUCCESS_READ)
//                )]
//            ),
//            ApiResponse(
//                responseCode = "404",
//                description = "검색 결과가 존재하지 않습니다.",
//                content = [Content(
//                    mediaType = "application/json",
//                    schema = Schema(example = JOB_POST_FAILURE_NOT_FOUND)
//                )]
//            )
//        ]
//    )
//    @Parameter(
//        name = "keyword",
//        description = "검색할 키워드",
//        required = true,
//        example = "1",
//        schema = Schema(type = "string")
//    )
    @GetMapping("/search/{keyword}")
    fun searchJobPost(
        @PathVariable("keyword") keyword: String,
        @ModelAttribute pageRequestDTO: PageRequestDTO
    ): ResponseEntity<Page<JobPostDTO>> {
        val result = jobPostService.searchJobPosts(keyword, pageRequestDTO)
        return ResponseEntity.ok(result)
    }

//    @Operation(
//        summary = "게시물의 지원 수 조회",
//        description = "게시물에 해당하는 지원서의 수를 조회합니다.",
//        responses = [
//            ApiResponse(
//                responseCode = "200",
//                description = "지원서 수를 성공적으로 조회했습니다.",
//                content = [Content(
//                    mediaType = "application/json",
//                    schema = Schema(example = JOB_POST_SUCCESS_READ)
//                )]
//            ),
//            ApiResponse(
//                responseCode = "404",
//                description = "지원서가 존재하지 않습니다.",
//                content = [Content(
//                    mediaType = "application/json",
//                    schema = Schema(example = JOB_POST_FAILURE_NOT_FOUND)
//                )]
//            )
//        ]
//    )
//    @Parameter(
//        name = "id",
//        description = "지원서의 해당 일거리 ID",
//        required = true,
//        example = "1",
//        schema = Schema(type = "integer")
//    )
    @GetMapping("/{id}/applyCount")
    fun getApplyCount(@PathVariable id: Long): ResponseEntity<Long> {
        val applyCount = applyService.getApplyCount(id)
        return ResponseEntity.ok(applyCount)
    }

//    @Operation(
//        summary = "남은 일자 기준 오름차순 조회",
//        description = "남은 일자가 적은 순으로 전체 일거리를 조회합니다.",
//        responses = [
//            ApiResponse(
//                responseCode = "200",
//                description = "일거리를 성공적으로 조회했습니다.",
//                content = [Content(
//                    mediaType = "application/json",
//                    schema = Schema(example = JOB_POST_SUCCESS_READ)
//                )]
//            ),
//            ApiResponse(
//                responseCode = "404",
//                description = "일거리가 존재하지 않습니다.",
//                content = [Content(
//                    mediaType = "application/json",
//                    schema = Schema(example = JOB_POST_FAILURE_NOT_FOUND)
//                )]
//            )
//        ]
//    )
    @GetMapping("/sortByRestDate")
    fun getSortByDay(@ModelAttribute pageRequestDTO: PageRequestDTO): ResponseEntity<Page<JobPostDTO>> =
        ResponseEntity.ok(jobPostService.getSortByDay(pageRequestDTO))

//    @Operation(
//        summary = "지원 수 기준 오름차순 조회",
//        description = "지원 수가 많은 순으로 전체 일거리를 조회합니다.",
//        responses = [
//            ApiResponse(
//                responseCode = "200",
//                description = "일거리를 성공적으로 조회했습니다.",
//                content = [Content(
//                    mediaType = "application/json",
//                    schema = Schema(example = JOB_POST_SUCCESS_READ)
//                )]
//            ),
//            ApiResponse(
//                responseCode = "404",
//                description = "일거리가 존재하지 않습니다.",
//                content = [Content(
//                    mediaType = "application/json",
//                    schema = Schema(example = JOB_POST_FAILURE_NOT_FOUND)
//                )]
//            )
//        ]
//    )
    @GetMapping("/sortByApplyCount")
    fun getSortByApplyCount(@ModelAttribute pageRequestDTO: PageRequestDTO): ResponseEntity<Page<JobPostDTO>> =
        ResponseEntity.ok(jobPostService.getSortByApplyCount(pageRequestDTO))

//    @Operation(
//        summary = "생성 일자 기준 오름차순 조회",
//        description = "생성 일자가 이른 순으로 전체 일거리를 조회합니다.",
//        responses = [
//            ApiResponse(
//                responseCode = "200",
//                description = "일거리를 성공적으로 조회했습니다.",
//                content = [Content(
//                    mediaType = "application/json",
//                    schema = Schema(example = JOB_POST_SUCCESS_READ)
//                )]
//            ),
//            ApiResponse(
//                responseCode = "404",
//                description = "일거리가 존재하지 않습니다.",
//                content = [Content(
//                    mediaType = "application/json",
//                    schema = Schema(example = JOB_POST_FAILURE_NOT_FOUND)
//                )]
//            )
//        ]
//    )
    @GetMapping("/sortByCreatedAt")
    fun getSortByCreatedAt(@ModelAttribute pageRequestDTO: PageRequestDTO): ResponseEntity<Page<JobPostDTO>> =
        ResponseEntity.ok(jobPostService.getSortByCreatedAt(pageRequestDTO))
}




