package org.tenten.bittakotlin.feed.constant

enum class FeedError(val code: Int, val message: String) {
    NOT_FOUND(404, "피드가 존재하지 않습니다."),
    CANNOT_FOUND(404, "피드가 존재하지 않습니다."),
    CANNOT_MODIFY_BAD_AUTHORITY(403, "피드를 수정할 권한이 없습니다."),
    CANNOT_DELETE_BAD_AUTHORITY(403, "피드를 삭제할 권한이 없습니다."),
}