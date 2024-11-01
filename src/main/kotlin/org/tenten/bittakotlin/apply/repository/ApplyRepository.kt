package org.tenten.bittakotlin.apply.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.tenten.bittakotlin.apply.dto.ApplyDTO
import org.tenten.bittakotlin.apply.entity.Apply
import org.tenten.bittakotlin.jobpost.entity.JobPost
import org.tenten.bittakotlin.profile.entity.Profile
import java.util.*

@Repository
interface ApplyRepository : JpaRepository<Apply, Long> {

    @Query("SELECT a FROM Apply a WHERE a.profile = :profile")
    fun findAllByMember(@Param("profile") profile: Profile): List<Apply>

    @Query("SELECT a FROM Apply a WHERE a.jobPost.id = :jobPostId")
    fun findAllByJobPostId(@Param("jobPostId") jobPostId: Long): List<Apply>

    @Query("SELECT a FROM Apply a WHERE a.id = :id")
    fun getApplyDTO(@Param("id") id: Long): Optional<Apply>

    @Query("SELECT a FROM Apply a WHERE a.jobPost = :jobPost")
    fun findAllByJobPost(@Param("jobPost") jobPost: JobPost): List<ApplyDTO>

    @Query("SELECT COUNT(a) FROM Apply a WHERE a.jobPost.id = :jobPostId")
    fun countByJobPostId(@Param("jobPostId") jobPostId: Long): Long
}
