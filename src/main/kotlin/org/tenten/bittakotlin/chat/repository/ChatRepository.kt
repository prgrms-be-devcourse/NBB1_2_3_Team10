package org.tenten.bittakotlin.chat.repository

import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.tenten.bittakotlin.chat.entity.Chat

@Repository
interface ChatRepository : JpaRepository<Chat, Long> {
    @Query("SELECT c FROM Chat c WHERE c.chatRoom.id = :chatRoomId ORDER BY c.id DESC")
    fun findAllByChatRoomIdOrderByIdDesc(@Param("chatRoomId") chatRoomId: Long, pageable: Pageable): Slice<Chat>
}