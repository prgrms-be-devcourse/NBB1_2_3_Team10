package org.tenten.bittakotlin.member.service


import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.tenten.bittakotlin.member.dto.MemberRequestDTO
import org.tenten.bittakotlin.member.dto.MemberResponseDTO
import org.tenten.bittakotlin.member.entity.Member
import org.tenten.bittakotlin.member.repository.MemberRepository
import org.tenten.bittakotlin.profile.service.ProfileService

@Service
@RequiredArgsConstructor
@Slf4j
class MemberServiceImpl  (
    private val memberRepository: MemberRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
    private val profileService: ProfileService
  
): MemberService {

    override fun join(joinDTO: MemberRequestDTO.Join) {
        val username = joinDTO.username
        val password = joinDTO.password
        val nickname = joinDTO.nickname
        val address = joinDTO.address

        val isExist = memberRepository.existsByUsername(username)

        if (isExist) {
            return
        }

        val data = Member(
            username = username,
            password = bCryptPasswordEncoder.encode(password),
            nickname = nickname,
            address = address,
            role = "ROLE_USER"
        )

        //memberRepository.save(data)

        //Member 생성시 Profile 도 같이 생성되게 코드 생성
        val savedMember = memberRepository.save(data)

        profileService.createDefaultProfile(savedMember)

        memberRepository.save(data)
    }

    override fun read(id: Long): MemberResponseDTO.Information {
        val member = memberRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Member not found.") }

        return MemberResponseDTO.Information(
            id = member.id!!,
            username = member.username,
            nickname = member.nickname,
            address = member.address
        )
    }

    override fun updateMember(request: MemberRequestDTO.UpdateMemberRequest) {
        val member = memberRepository.findById(request.id)
            .orElseThrow { IllegalArgumentException("Member not found.") }

        // 비밀번호 변경 요청이 있을 경우
        if (request.beforePassword != null && request.afterPassword != null) {
            if (!bCryptPasswordEncoder.matches(request.beforePassword, member.password)) {
                throw IllegalArgumentException("Incorrect previous password.")
            }
            // 비밀번호 암호화 및 업데이트
            member.password = bCryptPasswordEncoder.encode(request.afterPassword)
        }

        // 주소 및 닉네임 업데이트 요청이 있을 경우
        request.nickname?.let { member.nickname = it }
        request.address?.let { member.address = it }

        member.username = request.username // 아이디는 항상 업데이트

        memberRepository.save(member) // 수정 후 저장
    }

}