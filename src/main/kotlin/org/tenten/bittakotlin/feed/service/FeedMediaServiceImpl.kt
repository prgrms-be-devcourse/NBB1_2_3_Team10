package org.tenten.bittakotlin.feed.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.tenten.bittakotlin.feed.entity.Feed
import org.tenten.bittakotlin.feed.entity.FeedMedia
import org.tenten.bittakotlin.feed.entity.key.FeedMediaId
import org.tenten.bittakotlin.feed.repository.FeedMediaRepository
import org.tenten.bittakotlin.media.dto.MediaRequestDto
import org.tenten.bittakotlin.media.dto.MediaResponseDto
import org.tenten.bittakotlin.media.service.MediaService
import org.tenten.bittakotlin.profile.entity.Profile

@Service
class FeedMediaServiceImpl(
    private val feedMediaRepository: FeedMediaRepository,

    private val mediaService: MediaService
) : FeedMediaService {
    @Transactional
    override fun save(feed: Feed, profile: Profile, uploadRequestDtos: List<MediaRequestDto.Upload>): List<MediaResponseDto.Read> {
        val responseDto: MutableList<MediaResponseDto.Read> = mutableListOf()

        uploadRequestDtos.forEach { uploadRequestDto ->
            val mediaResponseDto: MediaResponseDto.Upload = mediaService.upload(uploadRequestDto, profile)
            val media = mediaResponseDto.media

            feedMediaRepository.save(FeedMedia(
                id = FeedMediaId(feedId = feed.id!!, mediaId = media.id!!),
                feed = feed,
                media = media
            ))

            responseDto.add(MediaResponseDto.Read(
                filename = mediaResponseDto.media.filename,
                url = mediaResponseDto.url
            ))
        }

        return responseDto
    }

    @Transactional
    override fun delete(deleteRequestDto: List<MediaRequestDto.Delete>) {
        deleteRequestDto.forEach { deleteRequestDto ->
            val id: Long = mediaService.delete(deleteRequestDto)

            feedMediaRepository.deleteByMediaId(id)
        }
    }

    @Transactional
    override fun delete(feedId: Long) {
        val filenames: List<String> = feedMediaRepository.findFilenamesByFeedId(feedId)

        filenames.forEach { filename ->
            val id: Long = mediaService.delete(MediaRequestDto.Delete(
                filename = filename
            ))

            feedMediaRepository.deleteByMediaId(id)
        }
    }

    @Transactional(readOnly = true)
    override fun getMedias(feedId: Long): List<MediaResponseDto.Read> {
        val filenames: List<String> = feedMediaRepository.findFilenamesByFeedId(feedId)

        val medias: MutableList<MediaResponseDto.Read> = mutableListOf()

        filenames.forEach { filename ->
            medias.add(mediaService.read(MediaRequestDto.Read(
                filename = filename
            )))
        }

        return medias
    }
}