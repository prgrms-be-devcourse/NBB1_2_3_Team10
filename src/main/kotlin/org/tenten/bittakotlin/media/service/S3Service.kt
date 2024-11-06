package org.tenten.bittakotlin.media.service

import org.tenten.bittakotlin.media.dto.MediaResponseDto

interface S3Service {
    fun getReadUrl(name: String): String

    fun getUploadUrl(name: String, contentType: String): String

    fun getPublicUploadUrl(name: String, contentType: String): MediaResponseDto.PublicUpload

    fun delete(filename: String): Unit
}