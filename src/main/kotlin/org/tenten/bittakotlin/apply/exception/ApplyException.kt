package org.tenten.bittakotlin.apply.exception

enum class ApplyException(private val applyTaskException: ApplyTaskException) {
    NOT_FOUND("지원서를 찾을 수 없습니다", 404),
    NOT_REGISTERED("지원서가 등록되지 않았습니다", 400),
    NOT_REMOVED("지원서가 삭제되지 않았습니다", 400),
    NOT_FETCHED("지원서 조회에 실패하였습니다", 400);

    constructor(message: String, code: Int) : this(ApplyTaskException(message, code))

    fun get(): ApplyTaskException = applyTaskException
}


