package org.tenten.bittakotlin.member.exception

enum class MemberException (private val memberTaskException: MemberTaskException) {

    NOT_FOUND("회원이 존재하지 않습니다.", 404),        // 회원을 찾을 수 없는 경우
    DUPLICATE("사용자 이름이 이미 존재합니다.", 409), // 중복된 사용자 이름
    INVALID("유효하지 않은 회원 데이터입니다.", 400),       // 유효하지 않은 회원 데이터
    BAD_CREDENTIAL("잘못된 사용자 이름 또는 비밀번호입니다.", 401), // 잘못된 자격 증명
    ACCESS_DENIED("접근이 거부되었습니다.", 403);      // 접근 거부

    constructor(message: String, code: Int) : this(MemberTaskException(message, code))
    fun get() : MemberTaskException = memberTaskException

}