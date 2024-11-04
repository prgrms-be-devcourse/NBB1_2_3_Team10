package org.tenten.bittakotlin.chat.service

import org.springframework.data.domain.Pageable
import org.tenten.bittakotlin.chat.dto.ChatRequestDto
import org.tenten.bittakotlin.chat.dto.ChatResponseDto

interface ChatService {
    fun get(pageable: Pageable, requestDto: ChatRequestDto.Read): List<ChatResponseDto.Read>

    fun save(requestDto: ChatRequestDto.Send): ChatResponseDto.Send

    fun delete(chatId: Long): Long?
}