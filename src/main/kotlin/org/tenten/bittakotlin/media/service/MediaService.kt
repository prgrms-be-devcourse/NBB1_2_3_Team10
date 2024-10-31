package org.tenten.bittakotlin.media.service

import org.tenten.bittakotlin.media.dto.MediaRequestDto

interface MediaService {
    fun read(requestDto: MediaRequestDto.Read): String

    fun upload(requestDto: MediaRequestDto.Upload): String

    fun delete(requestDto: MediaRequestDto.Delete): Unit
}