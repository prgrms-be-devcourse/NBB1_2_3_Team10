package org.tenten.bittakotlin.feed.service

import org.tenten.bittakotlin.feed.entity.Feed
import org.tenten.bittakotlin.feed.repository.FeedRepository
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@RequiredArgsConstructor
class FeedProvider {
    private val feedRepository: FeedRepository? = null

    @Transactional(readOnly = true)
    fun getById(id: Long): Feed? {
        return feedRepository?.findById(id)?.orElse(null)
    }
}