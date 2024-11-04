package org.tenten.bittakotlin.feed.repository

import org.tenten.bittakotlin.feed.entity.Feed
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface FeedRepository : JpaRepository<Feed, Long> {

    @Query("SELECT f FROM Feed f WHERE f.member.username LIKE %:username%")
    fun findAllLikeUsernameOrderByIdDesc(@Param("username") username: String, pageable: Pageable): Page<Feed>

    @Query("SELECT f FROM Feed f WHERE f.title LIKE %:title%")
    fun findAllLikeTitleOrderByIdDesc(@Param("title") title: String, pageable: Pageable): Page<Feed>

    @Query("SELECT f FROM Feed f WHERE f.member.username LIKE %:username% AND f.title LIKE %:title%")
    fun findAllLikeUsernameAndTitleOrderByIdDesc(
        @Param("username") username: String,
        @Param("title") title: String,
        pageable: Pageable
    ): Page<Feed>

    fun findAllByOrderByIdDesc(pageable: Pageable): Page<Feed>

    @Modifying
    @Query("DELETE FROM Feed f WHERE f.id = :id")
    fun deleteByIdAndReturnCount(@Param("id") id: Long): Int

    @Query(value = "SELECT * FROM feed ORDER BY RAND() LIMIT :limit", nativeQuery = true)
    fun findRandomFeeds(@Param("limit") limit: Int): List<Feed>

    fun existsByIdAndMember_Username(feedId: Long, username: String): Boolean
}