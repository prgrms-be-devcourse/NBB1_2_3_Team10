package org.tenten.bittakotlin.security.repository

import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.tenten.bittakotlin.security.entity.RefreshEntity
import java.util.Optional

@Repository
interface RefreshRepository : JpaRepository<RefreshEntity, Long> {
    fun existsByRefresh(refresh: String): Boolean

    @Transactional
    fun deleteByRefresh(refresh: String)

    @Query("SELECT r FROM RefreshEntity r WHERE r.refresh = :refresh")
    fun findByRefresh(refresh: String): Optional<RefreshEntity>
}