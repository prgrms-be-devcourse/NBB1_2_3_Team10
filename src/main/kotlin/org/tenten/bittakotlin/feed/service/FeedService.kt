package org.tenten.bittakotlin.feed.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.tenten.bittakotlin.feed.dto.FeedRequestDto
import org.tenten.bittakotlin.feed.dto.FeedResponseDto

interface FeedService {
    fun get(id: Long): FeedResponseDto.Read

    fun getAll(pageable: Pageable, username: String?, title: String?): Page<FeedResponseDto.Read>

    fun getRandom(pageSize: Int): Page<FeedResponseDto.Read>

    fun save(requestDto: FeedRequestDto.Create): FeedResponseDto.Create

    fun update(feedId: Long, requestDto: FeedRequestDto.Modify): FeedResponseDto.Modify

    fun delete(id: Long)
}