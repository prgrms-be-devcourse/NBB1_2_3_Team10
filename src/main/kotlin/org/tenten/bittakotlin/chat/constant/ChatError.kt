package org.tenten.bittakotlin.chat.constant

import org.springframework.http.HttpStatus

enum class ChatError(val code: Int, val message: String) {
    CANNOT_FOUND_MEMBER(HttpStatus.NOT_FOUND.value(), "채팅할 대상 회원을 찾을 수 없습니다."),
    CANNOT_FOUND_CHAT(HttpStatus.NOT_FOUND.value(), "채팅이 존재하지 않거나, 삭제되었습니다."),
    CANNOT_FOUND_CHATROOM(HttpStatus.NOT_FOUND.value(), "아직 대화하지 않은 상대입니다."),
    CANNOT_CHAT_CAUSE_BLOCKED(HttpStatus.FORBIDDEN.value(), "현재 대화할 수 없는 상대입니다."),
    ALREADY_DELETED_CHAT(HttpStatus.BAD_REQUEST.value(), "이미 삭제된 메시지입니다.")
}