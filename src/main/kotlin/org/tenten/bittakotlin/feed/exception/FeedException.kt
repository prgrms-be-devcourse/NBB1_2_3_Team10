package org.tenten.bittakotlin.feed.exception

import com.prgrms2.java.bitta.feed.exception.FeedTaskException


enum class FeedException(code: Int, message: String) {
    NOT_FOUND(404, "피드가 존재하지 않습니다."),
    BAD_REQUEST(400, "잘못된 요청입니다."),
    BAD_AUTHORITY(403, "권한이 없습니다."),
    CANNOT_INSERT(400, "피드를 등록할 수 없습니다."),
    CANNOT_FOUND(404, "피드가 존재하지 않습니다."),
    CANNOT_MODIFY(400, "피드를 수정할 수 없습니다."),
    CANNOT_DELETE(404, "삭제할 피드가 존재하지 않습니다"),
    INTERNAL_ERROR(500, "서버 내부에 오류가 발생했습니다.");

    private val feedTaskException: FeedTaskException

    init {
        feedTaskException = FeedTaskException(code, message)
    }

    fun get(): FeedTaskException {
        return feedTaskException
    }
}