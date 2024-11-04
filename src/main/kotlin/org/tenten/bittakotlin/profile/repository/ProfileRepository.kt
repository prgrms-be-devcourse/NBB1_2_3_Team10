package org.tenten.bittakotlin.profile.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.tenten.bittakotlin.profile.entity.Profile
import java.util.Optional


interface ProfileRepository : JpaRepository<Profile, Long> {
    fun findByMemberId(memberId: Long): Profile?

    @Query("SELECT p FROM Profile p WHERE p.nickname = :nickname")
    fun findByNickname(@Param("nickname") nickname: String): Optional<Profile>

    @Query("SELECT p FROM Profile p WHERE p.member.username = :username")
    fun findByUsername(@Param("username") username: String): Optional<Profile>
}