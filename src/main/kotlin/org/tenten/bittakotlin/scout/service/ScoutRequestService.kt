package org.tenten.bittakotlin.scout.service

//import com.prgrms2.java.bitta.feed.entity.Feed
//import com.prgrms2.java.bitta.member.entity.Member
//import com.prgrms2.java.bitta.member.service.MemberProvider
//import com.prgrms2.java.bitta.feed.service.FeedProvider
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.tenten.bittakotlin.scout.dto.ScoutDTO
import org.tenten.bittakotlin.scout.entity.ScoutRequest
import org.tenten.bittakotlin.scout.repository.ScoutRequestRepository

@Service
class ScoutRequestService(
    private val scoutRequestRepository: ScoutRequestRepository,
    private val feedProvider: FeedProvider,
    private val memberProvider: MemberProvider
) {

    @Transactional
    fun sendScoutRequest(scoutDTO: ScoutDTO): ScoutDTO {
        val request = dtoToEntity(scoutDTO)
        val savedRequest = scoutRequestRepository.save(request)
        return entityToDto(savedRequest)
    }

    @Transactional(readOnly = true)
    fun getSentScoutRequests(senderId: Long, pageable: Pageable): Page<ScoutDTO> {
        return scoutRequestRepository.findBySenderIdOrderById(senderId, pageable)
            .map { request -> entityToDto(request) }
    }

    @Transactional(readOnly = true)
    fun getReceivedScoutRequests(receiverId: Long, pageable: Pageable): Page<ScoutDTO> {
        return scoutRequestRepository.findByReceiverIdOrderById(receiverId, pageable)
            .map { request -> entityToDto(request) }
    }

    private fun entityToDto(request: ScoutRequest): ScoutDTO {
        return ScoutDTO(
            id = request.id,
            feedId = request.feed.id,
            senderId = request.sender.id,
            receiverId = request.receiver.id,
            description = request.description,
            sentAt = request.sentAt
        )
    }

    private fun dtoToEntity(scoutDTO: ScoutDTO): ScoutRequest {
        val feed = feedProvider.getById(scoutDTO.feedId)
        val sender = memberProvider.getById(scoutDTO.senderId)
        val receiver = memberProvider.getById(scoutDTO.receiverId)

        return ScoutRequest(
            id = scoutDTO.id,
            feed = feed,
            sender = sender,
            receiver = receiver,
            description = scoutDTO.description,
            sentAt = scoutDTO.sentAt
        )
    }
}