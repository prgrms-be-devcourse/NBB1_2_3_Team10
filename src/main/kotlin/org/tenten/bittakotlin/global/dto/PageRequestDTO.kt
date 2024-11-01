package org.tenten.bittakotlin.global.dto

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import kotlin.math.max

data class PageRequestDTO(
    @field:Min(1)
    var page: Int = 1,

    @field:Min(10)
    @field:Max(100)
    var size: Int = 10
) {
    fun getPageable(sort: Sort): Pageable {
        val pageNum = (page - 1).coerceAtLeast(0)
        val sizeNum = size.coerceAtLeast(10)

        return PageRequest.of(pageNum, sizeNum, sort)
    }
}
