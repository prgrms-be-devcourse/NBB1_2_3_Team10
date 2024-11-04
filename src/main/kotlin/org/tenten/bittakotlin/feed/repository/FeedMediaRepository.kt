package org.tenten.bittakotlin.feed.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.tenten.bittakotlin.feed.entity.FeedMedia

@Repository
interface FeedMediaRepository : JpaRepository<FeedMedia, Long> {
    @Query("SELECT fm.media.filename FROM FeedMedia fm WHERE fm.feed.id = :feedId")
    fun findFilenamesByFeedId(@Param("feedId") feedId: Long): List<String>

    @Query("DELETE FROM FeedMedia fm WHERE fm.media.id = :mediaId")
    fun deleteByMediaId(@Param("mediaId") mediaId: Long)
}