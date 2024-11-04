package org.tenten.bittakotlin.feed.service

import org.tenten.bittakotlin.feed.dto.FeedDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.multipart.MultipartFile
import org.tenten.bittakotlin.feed.dto.FeedRequestDto

interface FeedService {
    fun read(id: Long): FeedDTO

    fun readAll(pageable: Pageable, username: String?, title: String?): Page<FeedDTO>

    fun insert(feedDto: FeedDTO, files: List<MultipartFile>)

    fun update(feedDto: FeedRequestDto.Modify, filesToUpload: List<MultipartFile>, filesToDeletes: List<String>)

    fun delete(id: Long)

    fun readRandomFeeds(limit: Int): List<FeedDTO>

    fun checkAuthority(feedId: Long, memberId: String): Boolean
}