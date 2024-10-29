package org.tenten.bittakotlin.scout.exception

enum class ScoutException(code: Int, message: String) {
    NOT_FOUND(404, "스카우트 요청을 찾을 수 없습니다."),
    BAD_REQUEST(400, "잘못된 요청입니다."),
    INTERNAL_ERROR(500, "서버 내부에 오류가 발생했습니다.");

    private val scoutTaskException = ScoutTaskException(code, message)

    fun get(): ScoutTaskException {
        return scoutTaskException
    }
}