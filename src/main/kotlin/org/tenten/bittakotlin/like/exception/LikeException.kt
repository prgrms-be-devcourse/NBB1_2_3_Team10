package org.tenten.bittakotlin.like.exception

enum class LikeException(private val likeTaskException: LikeTaskException) {
    BAD_REQUEST("잘못된 접근입니다", 400),
    NOT_FOUND("좋아요를 찾을 수 없습니다", 404),
    NOT_REGISTERED("좋아요를 등록할 수 없습니다", 400),
    NOT_REMOVED("좋아요를 취소할 수 없습니다", 400),
    NOT_FETCHED("좋아요를 누른 회원을 조회할 수 없습니다", 400);

    constructor(message: String, code: Int) : this(LikeTaskException(message, code))

    fun get(): LikeTaskException = likeTaskException
}