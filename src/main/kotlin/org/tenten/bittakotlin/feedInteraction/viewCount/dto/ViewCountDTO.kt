package org.tenten.bittakotlin.feedInteraction.viewCount.dto

import lombok.Getter
import lombok.Setter

@Setter
@Getter
class ViewCountDTO(private val feedId: Long?, private val viewCount: Long) 