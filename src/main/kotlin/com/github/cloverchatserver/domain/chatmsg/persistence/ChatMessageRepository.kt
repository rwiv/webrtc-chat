package com.github.cloverchatserver.domain.chatmsg.persistence

import com.github.cloverchatserver.domain.chatroom.persistence.ChatRoom
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ChatMessageRepository : JpaRepository<ChatMessage, Long> {

    fun findByChatRoom(chatRoom: ChatRoom): List<ChatMessage>

    @Query("SELECT cm FROM ChatMessage cm " +
            "WHERE cm.chatRoom = :chatRoom " +
            "ORDER BY cm.createdAt DESC " +
            "LIMIT 1")
    fun findOneByChatRoom(chatRoom: ChatRoom): ChatMessage?
}
