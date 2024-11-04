package org.tenten.bittakotlin.feed.entity.key

import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
data class FeedMediaId(
    val feedId: Long,

    val mediaId: Long
) : Serializable