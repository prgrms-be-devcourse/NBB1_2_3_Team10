package org.tenten.bittakotlin.media.service

import jakarta.transaction.Transactional
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
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
class MediaServiceImpl(
    private val mediaRepository: MediaRepository,

    private val s3Service: S3Service,

    @Value("\${file.max.size.image}")
    private val imageMaxSize: Int,

    @Value("\${file.max.size.video}")
    private val videoMaxSize: Int
) : MediaService {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(MediaServiceImpl::class.java)
    }

    override fun read(requestDto: MediaRequestDto.Read): MediaResponseDto.Read {
        val result: Media = mediaRepository.findByFilename(requestDto.filename)
            .orElseThrow { MediaException(MediaError.CANNOT_FOUND) }

        return MediaResponseDto.Read(
            filename = result.filename,
            url = s3Service.getReadUrl(result.filename)
        )
    }

    override fun upload(requestDto: MediaRequestDto.Upload, profile: Profile): MediaResponseDto.Upload {
        val filetype: MediaType = checkMimetype(requestDto.mimetype)
        val filename: String = UUID.randomUUID().toString() + getExtension(requestDto.mimetype)
        val filesize: Int = checkFileSize(requestDto.filesize, filetype)

        val media: Media = mediaRepository.save(Media(
            filename = filename,
            filetype = filetype,
            filesize = filesize,
            profile = profile
        ))

        return MediaResponseDto.Upload(
            media = media,
            url = s3Service.getUploadUrl(filename, requestDto.mimetype)
        )
    }

    @Transactional
    override fun delete(requestDto: MediaRequestDto.Delete): Long {
        val filename: String = requestDto.filename

        val result: Media = mediaRepository.findByFilename(filename)
            .orElseThrow {
                logger.warn("The file data does not exist in DB: filename=$filename")

                MediaException(MediaError.CANNOT_FOUND)
            }

        val id = result.id!!

        s3Service.delete(result.filename)

        mediaRepository.deleteById(id)

        return id
    }

    private fun checkMimetype(mimetype: String): MediaType {
        if (mimetype.matches(Regex("image/(jpeg|png|gif|bmp|webp|svg\\+xml)"))) {
            logger.info("")
            return MediaType.IMAGE
        }

        if (mimetype.matches(Regex("video/(mp4|webm|ogg|x-msvideo|x-matroska)"))) {
            logger.info("")
            return MediaType.VIDEO
        }

        logger.warn("The file type is not valid: filetype=$mimetype")
        throw MediaException(MediaError.WRONG_MIME_TYPE)
    }

    private fun getExtension(mimetype: String): String? {
        val mimetypeMap = mapOf(
            "image/jpeg" to "jpg",
            "image/png" to "png",
            "image/gif" to "gif",
            "image/bmp" to "bmp",
            "image/webp" to "webp",
            "image/svg+xml" to "svg",
            "video/mp4" to "mp4",
            "video/webm" to "webm",
            "video/ogg" to "ogg",
            "video/x-msvideo" to "avi",
            "video/x-matroska" to "mkv"
        )
        return mimetypeMap[mimetype]
    }

    private fun checkFileSize(filesize: Int, type: MediaType): Int {
        val maxSize: Int = if (type == MediaType.IMAGE) {
            imageMaxSize
        } else {
            videoMaxSize
        }

        if (filesize > maxSize) {
            logger.warn("The file size exceeds the allowed limit: filesize=$filesize")
            throw MediaException(MediaError.WRONG_FILE_SIZE)
        }

        return filesize
    }
}