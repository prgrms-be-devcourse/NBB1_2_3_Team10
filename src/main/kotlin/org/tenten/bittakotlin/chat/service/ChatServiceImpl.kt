package org.tenten.bittakotlin.chat.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.stereotype.Service
import org.tenten.bittakotlin.chat.constant.ChatError
import org.tenten.bittakotlin.chat.dto.ChatRequestDto
import org.tenten.bittakotlin.chat.dto.ChatResponseDto
import org.tenten.bittakotlin.chat.dto.ChatRoomRequestDto
import org.tenten.bittakotlin.chat.entity.Chat
import org.tenten.bittakotlin.chat.entity.ChatRoom
import org.tenten.bittakotlin.chat.repository.ChatRepository
import org.tenten.bittakotlin.media.exception.ChatException
import org.tenten.bittakotlin.profile.service.ProfileService

@Service
class ChatServiceImpl(
    private val chatRepository: ChatRepository,

    private val chatRoomService: ChatRoomService,

    private val profileService: ProfileService
) : ChatService {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(ChatServiceImpl::class.java)
    }

    override fun get(pageable: Pageable, requestDto: ChatRequestDto.Read): List<ChatResponseDto.Read> {
        var result: MutableList<ChatResponseDto.Read> = mutableListOf()

        try {
            val chatRoom: ChatRoom = chatRoomService.getChatRoomByNicknames(requestDto.sender, requestDto.receiver)
            val chats: Slice<Chat> = chatRepository.findAllByChatRoomIdOrderByIdDesc(chatRoom.id!!, pageable)

            chats.forEach { chat -> result.add(
                ChatResponseDto.Read(
                    chatId = chat.id!!,
                    sender = chat.profile.nickname,
                    message = chat.message,
                    deleted = chat.deleted,
                    chatAt = chat.createdAt!!
                ))
            }
        } catch (e: NoSuchElementException) {
            logger.info("Cannot load chat list cause chatroom was not found.")
            throw ChatException(ChatError.CANNOT_FOUND_CHATROOM)
        }

        return result
    }

    override fun save(requestDto: ChatRequestDto.Send): ChatResponseDto.Send {
        var chatRoom: ChatRoom

        try {
            chatRoom = chatRoomService.getChatRoomByNicknames(requestDto.sender, requestDto.receiver)
        } catch (e: NoSuchElementException) {
            logger.info("Creating a new chatroom cause chatroom was not found.")
            chatRoom = chatRoomService.save(ChatRoomRequestDto.Create(
                nickname1 = requestDto.sender,
                nickname2 = requestDto.receiver
            ))
        }

        if (!chatRoom.activated) {
            throw ChatException(ChatError.CANNOT_CHAT_CAUSE_BLOCKED)
        }

        val chat: Chat = chatRepository.save(
            Chat(
                profile = profileService.getByNickname(requestDto.sender),
                message = requestDto.message,
                chatRoom = chatRoom,
                createdAt = null,
                updatedAt = null
            )
        )

        return ChatResponseDto.Send(
            chatRoomId = chatRoom.id!!,
            activated = chatRoom.activated,
            ChatResponseDto.Detail(
                chatId = chat.id!!,
                sender = chat.profile.nickname,
                message = chat.message,
                chatAt = chat.createdAt!!
            )
        )
    }

    override fun delete(chatId: Long): Long {
        var chat: Chat = getChatById(chatId)

        if (chat.deleted) {
            throw ChatException(ChatError.ALREADY_DELETED_CHAT)
        }

        chat.message = "삭제된 메시지 입니다."
        chat.deleted = true

        chatRepository.save(chat)

        return chat.chatRoom.id!!
    }

    private fun getChatById(chatId: Long): Chat {
        return chatRepository.findById(chatId)
            .orElseThrow { ChatException(ChatError.CANNOT_FOUND_CHAT) }
    }
}