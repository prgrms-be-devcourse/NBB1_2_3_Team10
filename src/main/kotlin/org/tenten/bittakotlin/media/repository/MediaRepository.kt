package org.tenten.bittakotlin.media.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.tenten.bittakotlin.media.entity.Media
import java.util.Optional

@Repository
interface MediaRepository : JpaRepository<Media, Long> {
    @Query("SELECT m FROM Media m WHERE m.filename = :filename")
    fun findByFilename(@Param("filename") filename: String): Optional<Media>

    @Query("SELECT m FROM Media m WHERE m.filename = :filename AND m.member = :member")
    fun findByFilenameAndUsername(@Param("filename") filename: String, @Param("member") member: String): Optional<Media>
}