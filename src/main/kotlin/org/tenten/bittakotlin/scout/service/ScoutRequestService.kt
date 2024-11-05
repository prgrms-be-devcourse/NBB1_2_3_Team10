package org.tenten.bittakotlin.scout.service


import jakarta.persistence.EntityNotFoundException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.tenten.bittakotlin.feed.repository.FeedRepository
import org.tenten.bittakotlin.profile.repository.ProfileRepository
import org.tenten.bittakotlin.scout.dto.ScoutDTO
import org.tenten.bittakotlin.scout.entity.ScoutRequest
import org.tenten.bittakotlin.scout.repository.ScoutRequestRepository


@Service
class ScoutRequestService(
    private val scoutRequestRepository: ScoutRequestRepository,
    private val feedRepository: FeedRepository,
    private val profileRepository: ProfileRepository
) {

    private val logger: Logger = LoggerFactory.getLogger(ScoutRequestService::class.java)

    @Transactional
    fun sendScoutRequest(scoutDTO: ScoutDTO): ScoutDTO {
        logger.info("Attempting to send scout request from senderId=${scoutDTO.senderId} to receiverId=${scoutDTO.receiverId}")

        val request = dtoToEntity(scoutDTO)
        val savedRequest = scoutRequestRepository.save(request)

        logger.info("Scout request successfully saved with id=${savedRequest.id}")
        return entityToDto(savedRequest)
    }

    @Transactional(readOnly = true)
    fun getSentScoutRequests(senderId: Long, pageable: Pageable): Page<ScoutDTO> {
        logger.info("Fetching sent scout requests for senderId=$senderId")

        val sentRequests = scoutRequestRepository.findBySenderIdOrderById(senderId, pageable)
            .map { request -> entityToDto(request) }

        logger.info("Retrieved ${sentRequests.totalElements} sent scout requests for senderId=$senderId")
        return sentRequests
    }

    @Transactional(readOnly = true)
    fun getReceivedScoutRequests(receiverId: Long, pageable: Pageable): Page<ScoutDTO> {
        logger.info("Fetching received scout requests for receiverId=$receiverId")

        val receivedRequests = scoutRequestRepository.findByReceiverIdOrderById(receiverId, pageable)
            .map { request -> entityToDto(request) }

        logger.info("Retrieved ${receivedRequests.totalElements} received scout requests for receiverId=$receiverId")
        return receivedRequests
    }

    private fun entityToDto(request: ScoutRequest): ScoutDTO {
        return ScoutDTO(
            id = request.id,
            feedId = request.feed.id ?: throw IllegalStateException("Feed ID is missing"),
            senderId = request.sender.id ?: throw IllegalStateException("Sender Profile ID is missing"),
            receiverId = request.receiver.id ?: throw IllegalStateException("Receiver Profile ID is missing"),
            description = request.description,
            sentAt = request.sentAt
        )
    }

    private fun dtoToEntity(scoutDTO: ScoutDTO): ScoutRequest {
        logger.info("Converting ScoutDTO to ScoutRequest entity")

        val feed = feedRepository.findById(scoutDTO.feedId)
            .orElseThrow { EntityNotFoundException("Feed not found with id=${scoutDTO.feedId}") }

        val sender = profileRepository.findById(scoutDTO.senderId)
            .orElseThrow { EntityNotFoundException("Sender not found with id=${scoutDTO.senderId}") }

        val receiver = profileRepository.findById(scoutDTO.receiverId)
            .orElseThrow { EntityNotFoundException("Receiver not found with id=${scoutDTO.receiverId}") }

        logger.info("ScoutRequest entity created with feedId=${feed.id}, senderId=${sender.id}, receiverId=${receiver.id}")

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

