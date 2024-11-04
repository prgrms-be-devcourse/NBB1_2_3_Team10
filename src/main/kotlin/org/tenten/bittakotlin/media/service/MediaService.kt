package org.tenten.bittakotlin.media.service

import org.tenten.bittakotlin.media.dto.MediaRequestDto
import org.tenten.bittakotlin.media.dto.MediaResponseDto
import org.tenten.bittakotlin.profile.entity.Profile

interface MediaService {
    fun read(requestDto: MediaRequestDto.Read): MediaResponseDto.Read

    fun upload(requestDto: MediaRequestDto.Upload, profile: Profile): MediaResponseDto.Upload

    fun delete(requestDto: MediaRequestDto.Delete): Long
}