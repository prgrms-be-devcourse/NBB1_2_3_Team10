package org.tenten.bittakotlin.media.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.tenten.bittakotlin.media.constant.MediaError
import org.tenten.bittakotlin.media.dto.MediaResponseDto
import org.tenten.bittakotlin.media.exception.MediaException
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.HeadObjectRequest
import software.amazon.awssdk.services.s3.model.NoSuchKeyException
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest
import java.time.Duration

@Service
class S3ServiceImpl(
    @Value("\${s3.bucket.name}")
    private val s3Bucket: String,

    private val s3Client: S3Client,

    private val s3Presigner: S3Presigner
) : S3Service {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(S3ServiceImpl::class.java)
    }

    override fun getReadUrl(name: String): String {
        existsInBucket(name)

        val presignedGetRequest: PresignedGetObjectRequest = s3Presigner.presignGetObject { request -> request
            .signatureDuration(Duration.ofMinutes(10))
            .getObjectRequest {getRequest -> getRequest
                .bucket(s3Bucket)
                .key(name)
                .build()
            }
        }

        return presignedGetRequest.url().toString()
    }

    override fun getUploadUrl(name: String, contentType: String): String {
        val presignedPutRequest: PresignedPutObjectRequest = s3Presigner.presignPutObject { request -> request
            .signatureDuration(Duration.ofMinutes(3))
            .putObjectRequest { putRequest -> putRequest
                .bucket(s3Bucket)
                .key(name)
                .contentType(contentType)
                .build()
            }
        }

        return presignedPutRequest.url().toString()
    }

    override fun getPublicUploadUrl(name: String, contentType: String): MediaResponseDto.PublicUpload {
        return MediaResponseDto.PublicUpload(
            uploadUrl = getUploadUrl(name, contentType),
            readUrl = "https://${s3Bucket}.s3.${Region.AP_NORTHEAST_2}.amazonaws.com/$name"
        )
    }

    override fun delete(name: String): Unit {
        existsInBucket(name)

        s3Client.deleteObject { delRequest -> delRequest
            .bucket(s3Bucket)
            .key(name)
            .build()
        }
    }

    private fun existsInBucket(name: String): Unit {
        try {
            val headRequest: HeadObjectRequest = HeadObjectRequest.builder()
                .bucket(s3Bucket)
                .key(name)
                .build()

            s3Client.headObject(headRequest)
        } catch (e: NoSuchKeyException) {
            logger.warn("Cannot found any file in bucket: $name")

            throw MediaException(MediaError.S3_CANNOT_FOUND)
        }
    }
}