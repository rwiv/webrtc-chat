package com.github.cloverchatserver.domain.chatuser.repository

import com.github.cloverchatserver.domain.chatroom.repository.ChatRoom
import com.github.cloverchatserver.domain.user.repository.Account
import org.springframework.data.jpa.repository.JpaRepository

interface ChatUserRepository : JpaRepository<ChatUser, Long> {

    fun findByChatRoom(chatRoom: ChatRoom): List<ChatUser>
    fun findBySessionId(sessionId: String): ChatUser?
    fun findByChatRoomAndAccount(chatRoom: ChatRoom, account: Account): List<ChatUser>
}