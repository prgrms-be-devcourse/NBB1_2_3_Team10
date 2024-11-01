package org.tenten.bittakotlin.member.service

import org.tenten.bittakotlin.member.dto.MemberRequestDTO
import org.tenten.bittakotlin.member.dto.MemberResponseDTO

interface MemberService {

    fun join(joinDTO: MemberRequestDTO.Join) // Join 기능 병합

    fun read(id: Long): MemberResponseDTO.Information

    fun updateMember(request: MemberRequestDTO.UpdateMemberRequest)

    fun remove(id: Long)

//    fun delete(id: Long)
//
//    fun checkAuthority(id: Long, username: String): Boolean
}