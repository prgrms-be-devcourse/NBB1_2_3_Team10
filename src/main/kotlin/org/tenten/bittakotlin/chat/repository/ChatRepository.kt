package org.tenten.bittakotlin.chat.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.tenten.bittakotlin.chat.entity.Chat

@Repository
interface ChatRepository : JpaRepository<Chat, Long> {
    @Query("SELECT c FROM Chat c WHERE c.chatRoom.id = :chatRoomId")
    fun findAllByChatRoomId(@Param("chatRoomId") chatRoomId: Long): List<Chat>
}