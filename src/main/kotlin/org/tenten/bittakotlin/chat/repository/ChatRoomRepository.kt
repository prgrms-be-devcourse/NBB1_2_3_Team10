package org.tenten.bittakotlin.chat.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.tenten.bittakotlin.chat.entity.ChatRoom

@Repository
interface ChatRoomRepository : JpaRepository<ChatRoom, Long> {
}