package org.tenten.bittakotlin.feed.service

import org.tenten.bittakotlin.feed.dto.FeedDTO
import org.tenten.bittakotlin.feed.entity.Feed
import org.tenten.bittakotlin.feed.exception.FeedException
import org.tenten.bittakotlin.feed.repository.FeedRepository
import org.tenten.bittakotlin.media.service.MediaService
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import org.tenten.bittakotlin.feed.dto.FeedRequestDto
import org.tenten.bittakotlin.member.repository.MemberRepository
import java.util.*
import java.util.stream.Collectors

@Service
@RequiredArgsConstructor
@Slf4j
class FeedServiceImpl(
    private val feedRepository: FeedRepository,
    private val mediaService: MediaService,
    private val memberRepository: MemberRepository,
) : FeedService {

    @Transactional(readOnly = true)
    override fun read(id: Long): FeedDTO {
        val feed = feedRepository.findById(id)
            .orElseThrow { FeedException.CANNOT_FOUND.get() }

        return entityToDto(feed)
    }


    @Transactional(readOnly = true)
    override fun readAll(pageable: Pageable, username: String?, title: String?): Page<FeedDTO> {
        val feeds = when {
            !username.isNullOrBlank() && !title.isNullOrBlank() ->
                feedRepository.findAllLikeUsernameAndTitleOrderByIdDesc(username, title, pageable)
            !username.isNullOrBlank() ->
                feedRepository.findAllLikeUsernameOrderByIdDesc(username, pageable)
            !title.isNullOrBlank() ->
                feedRepository.findAllLikeTitleOrderByIdDesc(title, pageable)
            else ->
                feedRepository.findAllByOrderByIdDesc(pageable)
        }

        return feeds.takeIf { it.hasContent() }
            ?.map { entityToDto(it) }
            ?: throw FeedException.CANNOT_FOUND.get()
    }


    @Transactional
    override fun insert(feedDTO: FeedDTO, files: List<MultipartFile>) {
        if (feedDTO.id != null) {
            throw FeedException.BAD_REQUEST.get()
        }

        var feed = dtoToEntity(feedDTO)
        feed = feedRepository.save(feed)


        mediaService.upload(files, feed.id)
    }



    @Transactional
    override fun update(feedDto: FeedRequestDto.Modify, filesToUpload: List<MultipartFile>, filesToDeletes: List<String>) {
        val feed = feedDto.id?.let {
            feedRepository.findById(it)
                .orElseThrow { FeedException.CANNOT_FOUND.get() }
        }

        feed?.title = feedDto.title.toString()
        feed?.content = feedDto.content

        if (!filesToDeletes.isNullOrEmpty()) {
            val deleteMedias = mediaService.getMedias(filesToDeletes)
            mediaService.deleteExistFiles(deleteMedias)
        }

        feed?.clearMedias()
        mediaService.uploads(filesToUpload, feedDto.id)

        feedRepository.save(feed)
    }


    @Transactional
    override fun delete(id: Long?) {
        val feed: Feed = feedRepository.findById(id)
            .orElseThrow(FeedException.CANNOT_FOUND::get)

        if (feed.getMedias() != null) {
            mediaService.deleteAll(feed.getMedias())
        }

        feed.setMember(null)
        feedRepository.delete(feed)
    }

    @Transactional(readOnly = true)
    override fun readRandomFeeds(limit: Int): List<FeedDTO> {
        val feeds: List<Feed> = feedRepository.findRandomFeeds(limit)
        return feeds.stream()
            .map<R> { feed: Feed -> this.entityToDto(feed) }
            .collect<List<FeedDTO>, Any>(Collectors.toList<Any>())
    }

    override fun checkAuthority(feedId: Long, username: String): Boolean {
        return feedRepository.existsByIdAndMember_Username(feedId, username)
    }

    private fun dtoToEntity(feedDto: FeedDTO): Feed {
        return Feed(
            id = feedDto.id,
            title = feedDto.title,
            content = feedDto.content,
            createdAt = feedDto.createdAt!!,
            member = memberRepository.findById(feedDto.memberId),
            medias = mediaService.getMedias(feedDto.medias)
        )
    }

    private fun entityToDto(feed: Feed): FeedDTO {
        return FeedDTO(
            id = feed.id,
            title = feed.title,
            content = feed.content,
            createdAt = feed.createdAt,
            memberId = feed.member.id!!,
            medias = mediaService.getUrls(feed.medias)
        )
    }

}