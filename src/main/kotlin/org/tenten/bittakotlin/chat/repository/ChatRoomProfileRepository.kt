package org.tenten.bittakotlin.chat.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.tenten.bittakotlin.chat.entity.ChatRoom
import org.tenten.bittakotlin.chat.entity.ChatRoomProfile
import org.tenten.bittakotlin.chat.entity.key.ChatRoomProfileId
import java.util.Optional

@Repository
interface ChatRoomProfileRepository : JpaRepository<ChatRoomProfile, ChatRoomProfileId> {
    @Query("SELECT crf.chatRoom " +
            "FROM ChatRoomProfile crf " +
            "WHERE crf.profile = :nickname1 OR crf.profile = :nickname2 " +
            "GROUP BY crf.chatRoom " +
            "HAVING COUNT(crf.chatRoom) = 2")
    fun findChatRoomByNicknames(@Param("nickname1") nickname1: String, @Param("nickname2") nickname2: String): Optional<ChatRoom>

    @Query("SELECT crf FROM ChatRoomProfile crf WHERE crf.chatRoom = :chatRoom AND crf.profile.nickname = :nickname")
    fun findChatRoomByChatRoomAndNickname(@Param("chatRoom") chatRoom: ChatRoom, @Param("nickname") nickname: String): ChatRoomProfile
}