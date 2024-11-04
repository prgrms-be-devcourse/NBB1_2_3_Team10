package org.tenten.bittakotlin.feed.service

import com.prgrms2.java.bitta.feed.exception.FeedException
import org.tenten.bittakotlin.feed.entity.Feed
import org.tenten.bittakotlin.feed.constant.FeedError
import org.tenten.bittakotlin.feed.repository.FeedRepository
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.tenten.bittakotlin.feed.dto.FeedRequestDto
import org.tenten.bittakotlin.feed.dto.FeedResponseDto
import org.tenten.bittakotlin.profile.entity.Profile
import org.tenten.bittakotlin.profile.service.ProfileService

@Service
@RequiredArgsConstructor
@Slf4j
class FeedServiceImpl(
    private val feedRepository: FeedRepository,

    private val profileService: ProfileService,

    private val feedMediaService: FeedMediaService,
) : FeedService {
    @Transactional(readOnly = true)
    override fun get(id: Long): FeedResponseDto.Read {
        val feed = feedRepository.findById(id)
            .orElseThrow { FeedException(FeedError.CANNOT_FOUND) }

        return toReadResponseDto(feed)
    }

    @Transactional(readOnly = true)
    override fun getAll(pageable: Pageable, username: String?, title: String?): Page<FeedResponseDto.Read> {
        val feeds: Page<Feed> = if (!username.isNullOrBlank() && !title.isNullOrBlank()) {
            feedRepository.findAllLikeNicknameAndTitleOrderByIdDesc(username, title, pageable)
        } else if (!username.isNullOrBlank()) {
            feedRepository.findAllLikeNicknameOrderByIdDesc(username, pageable)
        } else if (!title.isNullOrBlank()) {
            feedRepository.findAllLikeTitleOrderByIdDesc(title, pageable)
        } else {
            feedRepository.findAllByOrderByIdDesc(pageable)
        }

        if (feeds.isEmpty) {
            throw FeedException(FeedError.CANNOT_FOUND)
        }

        return feeds.map { feed -> toReadResponseDto(feed) }
    }

    override fun getRandom(pageSize: Int): Page<FeedResponseDto.Read> {
        val totalElements = feedRepository.count()

        if (totalElements == 0L) {
            throw FeedException(FeedError.CANNOT_FOUND)
        }

        val totalPages: Int = ((totalElements - 1) / pageSize).toInt()

        val randomPage: Int = (0 until totalPages).random()

        val pageable: Pageable = PageRequest.of(randomPage, pageSize)

        val feeds: Page<Feed> = feedRepository.findAllByOrderByIdDesc(pageable)

        return PageImpl(feeds.content.shuffled(), pageable, feeds.totalElements)
            .map { feed -> toReadResponseDto(feed) }
    }

    @Transactional
    override fun save(requestDto: FeedRequestDto.Create): FeedResponseDto.Create {
        val profile: Profile = profileService.getByPrincipal()

        val feed: Feed = feedRepository.save(Feed(
            title = requestDto.title,
            content = requestDto.content,
            profile = profile
        ))

        return if (requestDto.medias != null) {
            FeedResponseDto.Create(
                medias = feedMediaService.save(feed, profile, requestDto.medias))
        } else {
            FeedResponseDto.Create(emptyList())
        }
    }



    @Transactional
    override fun update(feedId: Long, requestDto: FeedRequestDto.Modify): FeedResponseDto.Modify {
        val profile: Profile = profileService.getByPrincipal()

        val feed: Feed = feedRepository.findById(feedId)
            .orElseThrow { FeedException(FeedError.CANNOT_FOUND) }

        if (profile == feed.profile) {
            throw FeedException(FeedError.CANNOT_MODIFY_BAD_AUTHORITY)
        }

        feed.title = requestDto.title
        feed.content = requestDto.content

        feedRepository.save(feed)

        val uploads = if (requestDto.uploads != null) {
            feedMediaService.save(feed, profile, requestDto.uploads)
        } else {
            null
        }

        if (requestDto.deletes != null) {
            feedMediaService.delete(requestDto.deletes)
        }

        return if (uploads != null) {
            FeedResponseDto.Modify(
                medias = uploads
            )
        } else {
            FeedResponseDto.Modify(emptyList())
        }
    }


    @Transactional
    override fun delete(id: Long) {
        val profile: Profile = profileService.getByPrincipal()

        val feed: Feed = feedRepository.findById(id)
            .orElseThrow { FeedException(FeedError.CANNOT_FOUND) }

        if (profile == feed.profile) {
            throw FeedException(FeedError.CANNOT_DELETE_BAD_AUTHORITY)
        }

        feedMediaService.delete(id)

        feedRepository.delete(feed)
    }

    private fun toReadResponseDto(feed: Feed): FeedResponseDto.Read {
        return FeedResponseDto.Read(
            id = feed.id!!,
            title = feed.title,
            content = feed.content,
            author = feed.profile.nickname,
            createdAt = feed.createdAt!!,
            medias = feedMediaService.getMedias(feed.id!!)
        )
    }
}