package org.tenten.bittakotlin.scout.service

import com.prgrms2.java.bitta.feed.entity.Feed
import lombok.RequiredArgsConstructor
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@RequiredArgsConstructor
class ScoutRequestService {
    private val scoutRequestRepository: ScoutRequestRepository? = null
    private val feedProvider: FeedProvider? = null
    private val memberProvider: MemberProvider? = null

    @Transactional
    fun sendScoutRequest(scoutDTO: ScoutDTO): ScoutDTO {
        var request: ScoutRequest = dtoToEntity(scoutDTO)

        request = scoutRequestRepository.save(request)

        return entityToDto(request)
    }


    @Transactional(readOnly = true)
    fun getSentScoutRequests(senderId: Long?, pageable: Pageable?): Page<ScoutDTO> {
        return scoutRequestRepository.findBySenderIdOrderById(senderId, pageable)
            .map { request: ScoutRequest -> this.entityToDto(request) }
    }


    @Transactional(readOnly = true)
    fun getReceivedScoutRequests(receiverId: Long?, pageable: Pageable?): Page<ScoutDTO> {
        return scoutRequestRepository.findByReceiverIdOrderById(receiverId, pageable)
            .map { request: ScoutRequest -> this.entityToDto(request) }
    }

    private fun entityToDto(request: ScoutRequest): ScoutDTO {
        return ScoutDTO.builder()
            .id(request.getId())
            .feedId(request.getFeed().getId())
            .senderId(request.getSender().getId())
            .receiverId(request.getReceiver().getId())
            .description(request.getDescription())
            .sentAt(request.getSentAt())
            .build()
    }

    private fun dtoToEntity(scoutDTO: ScoutDTO): ScoutRequest {
        val feed: Feed = feedProvider.getById(scoutDTO.getFeedId())
        val sender: Member = memberProvider.getById(scoutDTO.getSenderId())
        val receiver: Member = memberProvider.getById(scoutDTO.getReceiverId())

        return ScoutRequest.builder()
            .id(scoutDTO.getId())
            .feed(feed)
            .sender(sender)
            .receiver(receiver)
            .description(scoutDTO.getDescription())
            .sentAt(scoutDTO.getSentAt())
            .build()
    }
}