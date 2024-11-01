package org.tenten.bittakotlin.profile.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.tenten.bittakotlin.profile.entity.Profile


interface ProfileRepository : JpaRepository<Profile, Long> {
    fun findByMemberId(memberId: Long): Profile?
}