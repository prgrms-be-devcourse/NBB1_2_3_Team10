package org.tenten.bittakotlin.security.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.tenten.bittakotlin.member.entity.Member
import org.tenten.bittakotlin.member.repository.MemberRepository
import org.tenten.bittakotlin.security.dto.CustomUserDetails


@Service
class CustomUserDetailsService(private val memberRepository: MemberRepository) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails? {
        val memberData : Member? = memberRepository.findByUsername(username)

        return memberData?.let { CustomUserDetails(it) }
    }
}