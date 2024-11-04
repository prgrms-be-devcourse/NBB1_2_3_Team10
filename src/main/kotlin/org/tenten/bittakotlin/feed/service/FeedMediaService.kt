package org.tenten.bittakotlin.feed.service

import org.tenten.bittakotlin.feed.entity.Feed
import org.tenten.bittakotlin.media.dto.MediaRequestDto
import org.tenten.bittakotlin.media.dto.MediaResponseDto
import org.tenten.bittakotlin.profile.entity.Profile

interface FeedMediaService {
    fun save(feed: Feed, profile: Profile, uploadRequestDto: List<MediaRequestDto.Upload>): List<MediaResponseDto.Read>

    fun delete(deleteRequestDto: List<MediaRequestDto.Delete>)

    fun delete(feedId: Long)

    fun getMedias(feedId: Long): List<MediaResponseDto.Read>
}