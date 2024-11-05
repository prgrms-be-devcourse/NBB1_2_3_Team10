package org.tenten.bittakotlin.media.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.tenten.bittakotlin.media.constant.MediaError
import org.tenten.bittakotlin.media.constant.MediaType
import org.tenten.bittakotlin.media.dto.MediaRequestDto
import org.tenten.bittakotlin.media.dto.MediaResponseDto
import org.tenten.bittakotlin.media.entity.Media
import org.tenten.bittakotlin.media.exception.MediaException
import org.tenten.bittakotlin.media.repository.MediaRepository
import org.tenten.bittakotlin.profile.entity.Profile
import java.util.*

@Service
class ProfileImageServiceImpl (
    private val mediaRepository: MediaRepository,

    private val s3Service: S3Service,

    @Value("\${file.max.size.image}")
    private val maxsize: Int
) : ProfileImageService {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(ProfileImageServiceImpl::class.java)
    }

    @Transactional
    override fun upload(requestDto: MediaRequestDto.Upload, profile: Profile): MediaResponseDto.PublicUpload {
        val filetype: MediaType = checkMimetype(requestDto.mimetype)
        val filename: String = UUID.randomUUID().toString() + getExtension(requestDto.mimetype)
        val filesize: Int = checkFileSize(requestDto.filesize)

        mediaRepository.save(Media(
            filename = filename,
            filetype = filetype,
            filesize = filesize,
            profile = profile
        ))

        return s3Service.getPublicUploadUrl(filename, requestDto.mimetype)
    }

    @Transactional
    override fun delete(requestDto: MediaRequestDto.Delete) {
        val filename: String = requestDto.filename

        mediaRepository.findByFilename(filename).orElseThrow { MediaException(MediaError.CANNOT_FOUND) }
    }

    private fun getExtension(mimetype: String): String? {
        val mimetypeMap = mapOf(
            "image/jpeg" to "jpg",
            "image/png" to "png",
            "image/gif" to "gif",
            "image/bmp" to "bmp",
            "image/webp" to "webp",
            "image/svg+xml" to "svg",
        )
        return mimetypeMap[mimetype]
    }

    private fun checkMimetype(mimetype: String): MediaType {
        if (mimetype.matches(Regex("image/(jpeg|png|gif|bmp|webp|svg\\+xml)"))) {
            return MediaType.IMAGE
        }

        throw MediaException(MediaError.WRONG_MIME_TYPE)
    }

    private fun checkFileSize(filesize: Int): Int {
        if (filesize > maxsize) {
            throw MediaException(MediaError.WRONG_FILE_SIZE)
        }

        return filesize
    }
}