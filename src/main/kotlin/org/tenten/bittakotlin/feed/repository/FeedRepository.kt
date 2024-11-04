package org.tenten.bittakotlin.feed.repository

import org.tenten.bittakotlin.feed.entity.Feed
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface FeedRepository : JpaRepository<Feed, Long> {

    @Query("SELECT f FROM Feed f WHERE f.profile.nickname LIKE %:nickname% ORDER BY f.id DESC")
    fun findAllLikeNicknameOrderByIdDesc(@Param("nickname") nickname: String, pageable: Pageable): Page<Feed>

    @Query("SELECT f FROM Feed f WHERE f.title LIKE %:title% ORDER BY f.id DESC")
    fun findAllLikeTitleOrderByIdDesc(@Param("title") title: String, pageable: Pageable): Page<Feed>

    @Query("SELECT f FROM Feed f WHERE f.profile.nickname LIKE %:nickname% AND f.title LIKE %:title% ORDER BY f.id DESC")
    fun findAllLikeNicknameAndTitleOrderByIdDesc(@Param("nickname") nickname: String, @Param("title") title: String,
        pageable: Pageable): Page<Feed>

    fun findAllByOrderByIdDesc(pageable: Pageable): Page<Feed>
}