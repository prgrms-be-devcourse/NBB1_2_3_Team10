package org.tenten.bittakotlin.feed.entity

import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne
import jakarta.persistence.MapsId
import org.tenten.bittakotlin.feed.entity.key.FeedMediaId
import org.tenten.bittakotlin.media.entity.Media

@Entity
data class FeedMedia (
    @EmbeddedId
    val id: FeedMediaId? = null,

    @ManyToOne
    @MapsId("feedId")
    val feed: Feed,

    @ManyToOne
    @MapsId("mediaId")
    val media: Media
)