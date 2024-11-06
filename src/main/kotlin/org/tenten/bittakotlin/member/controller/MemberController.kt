package org.tenten.bittakotlin.member.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.web.bind.annotation.*
import org.tenten.bittakotlin.member.dto.MemberRequestDTO
import org.tenten.bittakotlin.member.dto.MemberResponseDTO
import org.tenten.bittakotlin.member.exception.MemberException
import org.tenten.bittakotlin.member.repository.MemberRepository
import org.tenten.bittakotlin.member.service.MemberService
import org.tenten.bittakotlin.security.util.JwtTokenUtil

@Tag(name = "회원관리 API 컨트롤러", description = "회원과 관련된 RestAPI 제공 컨트롤러")
@RestController
@RequestMapping("/api/v1/member")
class MemberController(
    private val memberService: MemberService,
    private val memberRepository: MemberRepository,
    private val jwtTokenUtil: JwtTokenUtil
) {

    @PostMapping("/login")
    fun login(@RequestBody requestDto: MemberRequestDTO.Login): ResponseEntity<Map<String, Any>> {
        val responseDto: MemberResponseDTO.Login = memberService.login(requestDto)

        val headers = HttpHeaders().apply {
            add(HttpHeaders.SET_COOKIE, getCookieString("accessToken", responseDto.accessToken, 3600, false))
            add(HttpHeaders.SET_COOKIE, getCookieString("refreshToken", responseDto.refreshToken, 604800, false))
            add(HttpHeaders.SET_COOKIE, getCookieString("profileId", responseDto.profileId.toString(), 604800, true))
            add(HttpHeaders.SET_COOKIE, getCookieString("profileUrl", responseDto.profileUrl, 604800, true))
        }

        val body = mapOf("message" to "로그인이 성공했습니다.")

        return ResponseEntity(body, headers, HttpStatus.OK)
    }

    @PostMapping("/logout")
    fun logout(request: HttpServletRequest, response: HttpServletResponse): ResponseEntity<Map<String, Any>> {
        val headers = HttpHeaders().apply {
            add(HttpHeaders.SET_COOKIE, getCookieString("accessToken", null, 0, false))
            add(HttpHeaders.SET_COOKIE, getCookieString("refreshToken", null, 0, false))
            add(HttpHeaders.SET_COOKIE, getCookieString("profileId", null, 0, true))
            add(HttpHeaders.SET_COOKIE, getCookieString("profileUrl", null, 0, true))
        }

        val body = mapOf("message" to "로그아웃이 성공했습니다.")

        return ResponseEntity(body, headers, HttpStatus.OK)
    }


    private fun getCookieString(name: String, token: String?, ageMax: Int, isPublic: Boolean): String {
        return buildString {
            append("$name=$token; Path=/; Max-Age=$ageMax;")
            if (!isPublic) append(" HttpOnly;")
        }
    }

    // 회원가입

    @Operation(
        summary = "회원가입",
        description = "회원가입을 진행합니다.",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "회원가입 성공",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(example = "MEMBER_SUCCESS_SIGN_UP") // 예시 값은 문자열로 처리
                )]
            ),
            ApiResponse(
                responseCode = "400",
                description = "회원가입 실패",
                content = [Content()]
            )
        ]
    )
    @PostMapping("/join")
    fun join(@RequestBody joinDTO: MemberRequestDTO.Join): ResponseEntity<MemberRequestDTO.Join> {
        memberService.join(joinDTO)
        return ResponseEntity.ok(joinDTO)
    }

    // 회원 정보 조회

    @Operation(
        summary = "회원 정보 조회",
        description = "회원의 ID를 사용해 회원 정보를 조회합니다.",
        responses = [ApiResponse(
            responseCode = "200",
            description = "회원 정보 조회 성공",
            content = [Content(mediaType = "application/json")]
        ), ApiResponse(responseCode = "404", description = "회원 정보 조회 실패", content = [Content()])]
    )
    @GetMapping("/{id}")
    fun read(@PathVariable id: Long): ResponseEntity<MemberResponseDTO.Information> {
        val memberInfo = memberService.read(id)
        return ResponseEntity(memberInfo, HttpStatus.OK)
    }


    @PutMapping("/{id}")
    fun updateMember(
        @PathVariable id: Long,
        @RequestBody updateRequest: MemberRequestDTO.UpdateMemberRequest,
        @RequestHeader("access") token: String // JWT 토큰을 헤더에서 추출
    ): ResponseEntity<Void> {
        // 현재 로그인한 사용자 username 추출
        val usernameFromToken = jwtTokenUtil.getUsername(token)

        // id로 회원 정보 조회
        val member = memberRepository.findById(id)
            .orElseThrow { MemberException.NOT_FOUND.get() }

        // username 비교
        if (member.username != usernameFromToken) {
            throw AccessDeniedException("You don't have permission to update this member.")
        }

        // 유효성 검증 후 회원 정보 업데이트
        memberService.updateMember(updateRequest, id) // id를 포함하여 업데이트 메서드 호출
        return ResponseEntity.ok().build()
    }


    @DeleteMapping("/{id}")
    fun remove(@PathVariable id: Long, @RequestHeader("access") token: String): ResponseEntity<String> {
        val username = jwtTokenUtil.getUsername(token)
        val member = memberRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Member not Found.") }
        if(member.username != username) {
            throw AccessDeniedException("You don't have permission to delete this member.")
        }
        memberRepository.delete(member)
        return ResponseEntity.ok("탈퇴 성공")
    }
}