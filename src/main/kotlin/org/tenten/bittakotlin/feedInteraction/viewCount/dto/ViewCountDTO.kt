package org.tenten.bittakotlin.feedInteraction.viewCount.dto

import lombok.Getter
import lombok.Setter


data class ViewCountDTO(
    val feedId: Long?,
    val viewCount: Long
)