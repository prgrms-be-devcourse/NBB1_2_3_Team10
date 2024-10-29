package org.tenten.bittakotlin.security.repository

import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.tenten.bittakotlin.security.entity.RefreshEntity

@Repository
interface RefreshRepository : JpaRepository<RefreshEntity, Long> {

    fun existsByRefresh(refresh: String): Boolean

    @Transactional
    fun deleteByRefresh(refresh: String)
}