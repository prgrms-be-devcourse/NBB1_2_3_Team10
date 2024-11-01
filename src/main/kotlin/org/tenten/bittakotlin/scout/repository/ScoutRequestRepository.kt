package org.tenten.bittakotlin.scout.repository


import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.tenten.bittakotlin.scout.entity.ScoutRequest

interface ScoutRequestRepository : JpaRepository<ScoutRequest, Long> {
    fun findBySenderIdOrderById(senderId: Long, pageable: Pageable): Page<ScoutRequest>
    fun findByReceiverIdOrderById(receiverId: Long, pageable: Pageable): Page<ScoutRequest>
}