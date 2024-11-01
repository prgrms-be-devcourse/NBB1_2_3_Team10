package org.tenten.bittakotlin.jobpost.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.tenten.bittakotlin.jobpost.entity.JobPost
import java.util.Optional

interface JobPostRepository : JpaRepository<JobPost, Long> {

    @Query("SELECT j FROM JobPost j WHERE j.id = :id")
    fun getJobPost(@Param("id") id: Long): Optional<JobPost>

    @Query("SELECT j FROM JobPost j ORDER BY j.id DESC")
    fun getList(pageable: Pageable): Page<JobPost>

    @Query("SELECT j FROM JobPost j WHERE j.profile.id = :profileId")
    fun findJobPostByMember(@Param("profileId") profileId: Long, pageable: Pageable): Page<JobPost>

    @Query("SELECT j FROM JobPost j WHERE LOWER(j.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(j.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    fun searchByKeyword(@Param("keyword") keyword: String, pageable: Pageable): Page<JobPost>

    @Query("SELECT j FROM JobPost j WHERE j.restDate IS NOT NULL ORDER BY j.restDate ASC")
    fun findSortedByRestDate(pageable: Pageable): Page<JobPost>

    @Query("SELECT j FROM JobPost j WHERE j.applyCount IS NOT NULL ORDER BY j.applyCount DESC")
    fun findSortedByApplyCountDesc(pageable: Pageable): Page<JobPost>

    @Query("SELECT j FROM JobPost j WHERE j.createdAt IS NOT NULL ORDER BY j.createdAt ASC")
    fun findSortedByCreatedAt(pageable: Pageable): Page<JobPost>
}

