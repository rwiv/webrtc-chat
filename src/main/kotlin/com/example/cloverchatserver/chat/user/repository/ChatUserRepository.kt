package com.example.cloverchatserver.chat.user.repository

import com.example.cloverchatserver.board.repository.ChatRoom
import com.example.cloverchatserver.user.repository.User
import org.springframework.data.jpa.repository.JpaRepository

interface ChatUserRepository : JpaRepository<ChatUser, Long> {

    fun findByChatRoom(chatRoom: ChatRoom): List<ChatUser>

    fun findByChatRoomAndUser(chatRoom: ChatRoom, user: User): ChatUser?
}