package org.tenten.bittakotlin.chat.service

import org.tenten.bittakotlin.chat.dto.ChatRequestDto
import org.tenten.bittakotlin.chat.dto.ChatResponseDto

interface ChatService {
    fun get(requestDto: ChatRequestDto.Read): List<ChatResponseDto.Read>

    fun save(requestDto: ChatRequestDto.Send): ChatResponseDto.Send

    fun delete(chatId: Long): Long?
}