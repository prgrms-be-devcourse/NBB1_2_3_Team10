package org.tenten.bittakotlin.jobpost.exception

enum class JobPostException(private val jobPostTaskException: JobPostTaskException) {
    BAD_REQUEST("잘못된 접근입니다", 400),
    NOT_FOUND("게시글을 찾을 수 없습니다", 404),
    NOT_REGISTERED("게시글을 등록할 수 없습니다", 400),
    NOT_MODIFIED("게시글을 수정할 수 없습니다", 400),
    NOT_REMOVED("게시글을 삭제할 수 없습니다", 400),
    NOT_FETCHED("게시글을 조회할 수 없습니다", 400);

    constructor(message: String, code: Int) : this(JobPostTaskException(message, code))

    fun get(): JobPostTaskException = jobPostTaskException
}

