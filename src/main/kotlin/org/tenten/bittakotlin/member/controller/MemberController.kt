package org.tenten.bittakotlin.member.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.web.bind.annotation.*
import org.tenten.bittakotlin.member.dto.MemberRequestDTO
import org.tenten.bittakotlin.member.dto.MemberResponseDTO
import org.tenten.bittakotlin.member.exception.MemberException
import org.tenten.bittakotlin.member.repository.MemberRepository
import org.tenten.bittakotlin.member.service.MemberService
import org.tenten.bittakotlin.security.jwt.JWTUtil

@RestController
@RequestMapping("/api/member")
class MemberController(
    private val memberService: MemberService,
    private val jwtUtil: JWTUtil,
    private val memberRepository: MemberRepository
) {

    // 회원가입
    @PostMapping("/join")
    fun join(@RequestBody joinDTO: MemberRequestDTO.Join): ResponseEntity<MemberRequestDTO.Join> {
        memberService.join(joinDTO)
        return ResponseEntity.ok(joinDTO)
    }

    // 회원 정보 조회
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
        val usernameFromToken = jwtUtil.getUsername(token)

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
        val username = jwtUtil.getUsername(token)
        val member = memberRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Member not Found.") }
        if(member.username != username) {
            throw AccessDeniedException("You don't have permission to delete this member.")
        }
        memberRepository.delete(member)
        return ResponseEntity.ok("탈퇴 성공")
    }
}