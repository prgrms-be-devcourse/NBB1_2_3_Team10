package org.tenten.bittakotlin.media.service

import org.tenten.bittakotlin.media.dto.MediaRequestDto
import org.tenten.bittakotlin.media.dto.MediaResponseDto
import org.tenten.bittakotlin.profile.entity.Profile

interface ProfileImageService {
    fun upload(requestDto: MediaRequestDto.Upload, profile: Profile): MediaResponseDto.PublicUpload

    fun delete(requestDto: MediaRequestDto.Delete)
}