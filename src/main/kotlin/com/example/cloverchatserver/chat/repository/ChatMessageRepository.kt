package com.example.cloverchatserver.chat.repository

import com.example.cloverchatserver.board.repository.ChatRoom
import org.springframework.data.jpa.repository.JpaRepository

interface ChatMessageRepository : JpaRepository<ChatMessage, Long> {

    fun findByChatRoom(chatRoom: ChatRoom): List<ChatMessage>
}