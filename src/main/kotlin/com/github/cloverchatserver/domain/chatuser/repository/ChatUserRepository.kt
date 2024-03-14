package com.github.cloverchatserver.domain.chatuser.repository

import com.github.cloverchatserver.domain.chatroom.repository.ChatRoom
import com.github.cloverchatserver.domain.user.repository.User
import org.springframework.data.jpa.repository.JpaRepository

interface ChatUserRepository : JpaRepository<ChatUser, Long> {

    fun findByChatRoom(chatRoom: ChatRoom): List<ChatUser>
//    fun findByUser(user: User): List<ChatUser>
    fun findBySessionId(sessionId: String): ChatUser?
    fun findByChatRoomAndUser(chatRoom: ChatRoom, user: User): List<ChatUser>
}