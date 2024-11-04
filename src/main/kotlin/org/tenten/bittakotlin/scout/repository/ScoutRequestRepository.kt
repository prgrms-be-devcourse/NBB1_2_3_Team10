package org.tenten.bittakotlin.scout.repository


import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.tenten.bittakotlin.scout.entity.ScoutRequest

interface ScoutRequestRepository : JpaRepository<ScoutRequest, Long> {
    fun findBySender_IdOrderById(senderId: Long, pageable: Pageable): Page<ScoutRequest>
    fun findByReceiver_IdOrderById(receiverId: Long, pageable: Pageable): Page<ScoutRequest>
}