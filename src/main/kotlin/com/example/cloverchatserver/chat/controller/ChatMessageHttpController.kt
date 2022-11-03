package com.example.cloverchatserver.chat.controller

import com.example.cloverchatserver.chat.repository.ChatMessage
import com.example.cloverchatserver.chat.service.ChatMessageService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/chat")
class ChatMessageHttpController(
    val chatMessageService: ChatMessageService
) {

    @GetMapping("/{chatRoomId}")
    fun getChatMessagesByChatRoomId(@PathVariable("chatRoomId") chatRoomId: Long): ResponseEntity<List<ChatMessage>> {
        val chatMessages = chatMessageService.getChatMessagesBy(chatRoomId)

        return ResponseEntity.ok().body(chatMessages)
    }
}