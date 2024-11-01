package org.tenten.bittakotlin.chat.controller

import org.springframework.http.ResponseEntity
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.tenten.bittakotlin.chat.dto.ChatRequestDto
import org.tenten.bittakotlin.chat.dto.ChatResponseDto
import org.tenten.bittakotlin.chat.dto.ChatRoomRequestDto
import org.tenten.bittakotlin.chat.service.ChatRoomService
import org.tenten.bittakotlin.chat.service.ChatService
import org.tenten.bittakotlin.media.dto.MediaRequestDto
import org.tenten.bittakotlin.media.service.MediaService

@RestController
@RequestMapping("/api/v1/chat")
class ChatController(
    private val simpMessagingTemplate: SimpMessagingTemplate,

    private val chatService: ChatService,

    private val chatRoomService: ChatRoomService
) {
    @MessageMapping("/send")
    fun send(@RequestBody requestDto: ChatRequestDto.Send): Unit {
        val responseDto: ChatResponseDto.Send = chatService.save(requestDto)

        simpMessagingTemplate.convertAndSend("/room/${responseDto.chatRoomId}", responseDto)
    }

    @GetMapping("/room")
    fun read(@RequestBody requestDto: ChatRequestDto.Read): ResponseEntity<Map<String, Any>> {
        return ResponseEntity.ok(mapOf(
            "message" to "파일 조회 링크를 성공적으로 생성했습니다.",
            "result" to chatService.get(requestDto)
        ))
    }

    @PostMapping("/room")
    fun block(@RequestBody requestDto: ChatRoomRequestDto.Block): ResponseEntity<Map<String, Any>> {
        return ResponseEntity.ok(mapOf(
            "message" to if (chatRoomService.toggleBlocked(requestDto)) {
                "해당 회원을 차단 해제했습니다."
            } else {
                "해당 회원을 차단 설정했습니다."
            }
        ))
    }

    @DeleteMapping("/message/{chatId}")
    fun delete(@PathVariable chatId: Long): ResponseEntity<Map<String, Any>> {
        val chatRoomId: Long = chatService.delete(chatId)!!

        simpMessagingTemplate.convertAndSend("/room/$chatRoomId",
            ChatResponseDto.Delete(
                chatRoomId = chatRoomId,
                chatId = chatId,
                message = "삭제된 메시지 입니다."
            )
        )

        return ResponseEntity.ok(mapOf("message" to "메시지를 성공적으로 삭제했습니다."))
    }
}