package org.tenten.bittakotlin.scout.repository

import com.prgrms2.java.bitta.scout.entity.ScoutRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
interface ScoutRequestRepository : JpaRepository<ScoutRequest?, Long?> {
    fun findBySenderIdOrderById(senderId: Long?, pageable: Pageable?): Page<ScoutRequest?>?
    fun findByReceiverIdOrderById(receiverId: Long?, pageable: Pageable?): Page<ScoutRequest?>?
}