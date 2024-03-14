package com.github.cloverchatserver.domain.chatmsg.repository

import com.github.cloverchatserver.domain.chatroom.repository.ChatRoom
import org.springframework.data.jpa.repository.JpaRepository

interface ChatMessageRepository : JpaRepository<ChatMessage, Long> {

    fun findByChatRoom(chatRoom: ChatRoom): List<ChatMessage>
}