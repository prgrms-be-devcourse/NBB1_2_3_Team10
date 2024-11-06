package org.tenten.bittakotlin.member.service


import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.tenten.bittakotlin.member.dto.MemberRequestDTO
import org.tenten.bittakotlin.member.dto.MemberResponseDTO
import org.tenten.bittakotlin.member.entity.Member
import org.tenten.bittakotlin.member.exception.MemberException
import org.tenten.bittakotlin.member.repository.MemberRepository
import org.tenten.bittakotlin.profile.entity.Profile
import org.tenten.bittakotlin.profile.service.ProfileService
import org.tenten.bittakotlin.security.dto.TokenRequestDto
import org.tenten.bittakotlin.security.service.TokenService
import org.tenten.bittakotlin.security.util.JwtTokenUtil

@Service
@RequiredArgsConstructor
@Slf4j
class MemberServiceImpl  (
    private val memberRepository: MemberRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
    private val profileService: ProfileService,
    private val tokenService: TokenService
): MemberService {
    override fun login(requestDto: MemberRequestDTO.Login): MemberResponseDTO.Login {
        val member = memberRepository.findByUsername(requestDto.username)
            ?: throw MemberException.BAD_CREDENTIAL.get()

        if (!bCryptPasswordEncoder.matches(requestDto.password, member.password)) {
            throw MemberException.BAD_CREDENTIAL.get()
        }

        val tokenResponseDto = tokenService.create(TokenRequestDto.Create(
            username = member.username,
            role = member.role!!
        ))

        val profile: Profile = profileService.getByUsername(member.username)

        return MemberResponseDTO.Login(
            accessToken = tokenResponseDto.accessToken,
            refreshToken = tokenResponseDto.refreshToken,
            profileId = profile.id!!,
            profileUrl = profile.profileUrl!!
        )
    }

    override fun join(joinDTO: MemberRequestDTO.Join) {
        val username = joinDTO.username
        val password = joinDTO.password
        val nickname = joinDTO.nickname
        val address = joinDTO.address

        // 사용자 이름 중복 검사
        if (memberRepository.existsByUsername(username)) {
            throw MemberException.DUPLICATE.get() // 중복 시 예외 발생
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
            .orElseThrow { MemberException.NOT_FOUND.get() }

        return MemberResponseDTO.Information(
            id = member.id!!,
            username = member.username,
            nickname = member.nickname,
            address = member.address
        )
    }

    override fun updateMember(request: MemberRequestDTO.UpdateMemberRequest, id: Long) {
        val member = memberRepository.findById(id)
            .orElseThrow { MemberException.NOT_FOUND.get() }

        // 비밀번호 변경 요청이 있을 경우
        if (request.beforePassword != null && request.afterPassword != null) {
            if (!bCryptPasswordEncoder.matches(request.beforePassword, member.password)) {
                throw MemberException.BAD_CREDENTIAL.get()
            }
            // 비밀번호 암호화 및 업데이트
            member.password = bCryptPasswordEncoder.encode(request.afterPassword)
        }

        // 주소 및 닉네임 업데이트 요청이 있을 경우
        request.nickname?.let { member.nickname = it }
        request.address?.let { member.address = it }

        // username은 변경할 수 없으므로 해당 줄 제거

        memberRepository.save(member) // 수정 후 저장
    }

    override fun remove(id: Long) {

        if (!memberRepository.existsById(id)) {
            throw MemberException.NOT_FOUND.get()
        }
        memberRepository.deleteById(id)
    }

}