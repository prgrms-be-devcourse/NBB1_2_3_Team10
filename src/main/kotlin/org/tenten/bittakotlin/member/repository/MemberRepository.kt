package org.tenten.bittakotlin.member.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.tenten.bittakotlin.member.entity.Member

@Repository
interface MemberRepository : JpaRepository<Member, Long> {

    fun existsByUsername(username: String): Boolean

    fun findByUsername(username: String): Member?

    fun existsByIdAndUsername(id: Long, username: String): Boolean
}