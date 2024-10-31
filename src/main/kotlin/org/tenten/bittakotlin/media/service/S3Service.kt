package org.tenten.bittakotlin.media.service

interface S3Service {
    fun getReadUrl(name: String): String

    fun getUploadUrl(name: String, contentType: String): String

    fun delete(filename: String): Unit
}