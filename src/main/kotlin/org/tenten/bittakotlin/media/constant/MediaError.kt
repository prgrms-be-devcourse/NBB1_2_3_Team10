package org.tenten.bittakotlin.media.constant

import org.springframework.http.HttpStatus

enum class MediaError(val code: Int, val message: String) {
    WRONG_FILE_SIZE(HttpStatus.BAD_REQUEST.value(), "이미지 파일은 10MB, 비디오 파일은 30MB를 초과할 수 없습니다."),
    WRONG_MIME_TYPE(HttpStatus.BAD_REQUEST.value(), "올바르지 않은 파일 유형입니다."),
    CANNOT_FOUND(HttpStatus.NOT_FOUND.value(), "DB에 파일이 존재하지 않습니다."),
    S3_CANNOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR.value(), "S3 서버에 파일이 존재하지 않습니다.")
}