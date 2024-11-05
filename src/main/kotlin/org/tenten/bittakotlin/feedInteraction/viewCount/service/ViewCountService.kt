package org.tenten.bittakotlin.feedInteraction.viewCount.service

import org.tenten.bittakotlin.feedInteraction.viewCount.dto.ViewCountDTO

interface ViewCountService {
    fun addView(feedId: Long): ViewCountDTO
    fun getViewCount(feedId: Long): ViewCountDTO
}