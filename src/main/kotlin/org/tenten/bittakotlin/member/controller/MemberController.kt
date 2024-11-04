package org.tenten.bittakotlin.member.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.web.bind.annotation.*
import org.tenten.bittakotlin.member.dto.MemberRequestDTO
import org.tenten.bittakotlin.member.dto.MemberResponseDTO
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

    // 회원 정보 업데이트
    @PutMapping("/{id}")
    fun updateMember(
        @PathVariable id: Long,
        @RequestBody updateRequest: MemberRequestDTO.UpdateMemberRequest
    ): ResponseEntity<Void> {
        // id는 updateRequest에서 가져오는 것이 아니라, PathVariable로 받아온 id를 그대로 사용
        memberService.updateMember(updateRequest.copy(id = id)) // copy() 메서드를 사용하여 새로운 인스턴스를 생성
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