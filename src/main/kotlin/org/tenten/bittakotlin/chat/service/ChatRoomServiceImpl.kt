package org.tenten.bittakotlin.chat.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.tenten.bittakotlin.chat.constant.ChatError
import org.tenten.bittakotlin.chat.dto.ChatRoomRequestDto
import org.tenten.bittakotlin.chat.entity.ChatRoom
import org.tenten.bittakotlin.chat.entity.ChatRoomProfile
import org.tenten.bittakotlin.chat.repository.ChatRoomProfileRepository
import org.tenten.bittakotlin.chat.repository.ChatRoomRepository
import org.tenten.bittakotlin.media.exception.ChatException
import org.tenten.bittakotlin.profile.entity.Profile
import org.tenten.bittakotlin.profile.service.ProfileService

@Service
class ChatRoomServiceImpl(
    private val chatRoomRepository: ChatRoomRepository,

    private val chatRoomProfileRepository: ChatRoomProfileRepository,

    private val profileService: ProfileService
) : ChatRoomService {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(ChatRoomServiceImpl::class.java)
    }

    override fun getChatRoomByNicknames(nickname1: String, nickname2: String): ChatRoom {
        return chatRoomProfileRepository.findChatRoomByNicknames(nickname1, nickname2)
            .orElseThrow { throw NoSuchElementException() }
    }

    override fun save(requestDto: ChatRoomRequestDto.Create): ChatRoom {
        val nickname1: String = requestDto.nickname1
        val nickname2: String = requestDto.nickname2
        val chatRoom: ChatRoom = chatRoomRepository.save(ChatRoom())

        try {
            chatRoomProfileRepository.saveAll(listOf(
                createChatRoomProfile(chatRoom, nickname1),
                createChatRoomProfile(chatRoom, nickname2)
            ))
        } catch (e: NoSuchElementException) {
            logger.info("Cannot found any member with those nicknames: nickname1=$nickname1, nickname2=$nickname2");
            throw ChatException(ChatError.CANNOT_FOUND_MEMBER)
        }

        return chatRoom
    }

    private fun createChatRoomProfile(chatRoom: ChatRoom, nickname: String): ChatRoomProfile {
        val profile: Profile = profileService.getByNickname(nickname)

        return ChatRoomProfile(
            chatRoom = chatRoom,
            profile = profile
        )
    }

    override fun toggleBlocked(requestDto: ChatRoomRequestDto.Block): Boolean {
        val (requester, target) = requestDto
        var blocked: Boolean = false

        try {
            val chatRoom: ChatRoom = getChatRoomByNicknames(requester, target)
            val requesterProfile = chatRoomProfileRepository.findChatRoomByChatRoomAndNickname(chatRoom, requester);
            val targetProfile = chatRoomProfileRepository.findChatRoomByChatRoomAndNickname(chatRoom, target)

            requesterProfile.blocked = !requesterProfile.blocked
            chatRoomProfileRepository.save(requesterProfile)

            chatRoom.activated = requesterProfile.blocked && targetProfile.blocked
            chatRoomRepository.save(chatRoom)

            blocked = requesterProfile.blocked
        } catch (e: NoSuchElementException) {
            logger.error("Cannot found any chat room or member: requester=$requester, target=$target")
            throw ChatException(ChatError.CANNOT_FOUND_CHATROOM)
        }

        return blocked
    }
}