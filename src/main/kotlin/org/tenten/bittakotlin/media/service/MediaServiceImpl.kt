package org.tenten.bittakotlin.media.service

import jakarta.transaction.Transactional
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.tenten.bittakotlin.media.constant.MediaError
import org.tenten.bittakotlin.media.constant.MediaType
import org.tenten.bittakotlin.media.dto.MediaRequestDto
import org.tenten.bittakotlin.media.entity.Media
import org.tenten.bittakotlin.media.exception.MediaException
import org.tenten.bittakotlin.media.repository.MediaRepository
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

    override fun read(requestDto: MediaRequestDto.Read): String {
        val result: Media = mediaRepository.findByFilename(requestDto.filename)
            .orElseThrow { MediaException(MediaError.CANNOT_FOUND) }

        return s3Service.getReadUrl(result.filename)
    }

    override fun upload(requestDto: MediaRequestDto.Upload): String {
        val filename: String = UUID.randomUUID().toString()
        val filetype: MediaType = checkMimetype(requestDto.mimetype)
        val filesize: Int = checkFileSize(requestDto.filesize, filetype)

        mediaRepository.save(Media(
            filename = filename,
            filetype = filetype,
            filesize = filesize
        ))

        return s3Service.getUploadUrl(filename, requestDto.mimetype)
    }

    @Transactional
    override fun delete(requestDto: MediaRequestDto.Delete) {
        val filename: String = requestDto.filename
        val username: String = requestDto.username
        val result: Media = mediaRepository.findByFilenameAndUsername(filename, username)
            .orElseThrow {
                logger.warn("The file data does not exist in DB: filename=$filename, username=$username")

                MediaException(MediaError.CANNOT_FOUND)
            }

        s3Service.delete(result.filename)
        mediaRepository.deleteById(result.id!!)
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