package org.tenten.bittakotlin.feedInteraction.viewCount.service

import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.tenten.bittakotlin.feed.repository.FeedRepository
import org.tenten.bittakotlin.feedInteraction.viewCount.dto.ViewCountDTO
import org.tenten.bittakotlin.feedInteraction.viewCount.entity.ViewCount
import org.tenten.bittakotlin.feedInteraction.viewCount.repository.ViewCountRepository


@Service
class ViewCountServiceImpl(
    private val viewCountRepository: ViewCountRepository,
    private val feedRepository: FeedRepository
) : ViewCountService {

    @Transactional
    override fun addView(feedId: Long): ViewCountDTO {
        val feed = feedRepository.findById(feedId)
            .orElseThrow { EntityNotFoundException("Feed not found for id: $feedId") }

        val viewCount = viewCountRepository.findByFeed(feed).orElseGet {
            val newViewCount = ViewCount(feed = feed, count = 0L)
            viewCountRepository.save(newViewCount)
        }

        viewCount.count += 1
        viewCountRepository.save(viewCount)

        return ViewCountDTO(feed.id, viewCount.count)
    }

    @Transactional(readOnly = true)
    override fun getViewCount(feedId: Long): ViewCountDTO {
        val feed = feedRepository.findById(feedId)
            .orElseThrow { EntityNotFoundException("Feed not found for id: $feedId") }

        val count = viewCountRepository.findByFeed(feed)
            .map { it.count }
            .orElse(0L)

        return ViewCountDTO(feed.id, count)
    }
}