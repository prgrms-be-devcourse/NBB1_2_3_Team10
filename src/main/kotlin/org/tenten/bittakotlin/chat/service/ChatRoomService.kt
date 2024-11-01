package org.tenten.bittakotlin.chat.service

import org.tenten.bittakotlin.chat.dto.ChatRoomRequestDto
import org.tenten.bittakotlin.chat.entity.ChatRoom

interface ChatRoomService {
    fun getChatRoomByNicknames(nickname1: String, nickname2: String): ChatRoom

    fun save(requestDto: ChatRoomRequestDto.Create): ChatRoom

    fun toggleBlocked(requestDto: ChatRoomRequestDto.Block): Boolean
}