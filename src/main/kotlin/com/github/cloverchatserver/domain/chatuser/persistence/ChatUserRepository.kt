package com.github.cloverchatserver.domain.chatuser.persistence

import com.github.cloverchatserver.domain.chatroom.persistence.ChatRoom
import com.github.cloverchatserver.domain.account.persistence.Account
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository

interface ChatUserRepository : JpaRepository<ChatUser, Long> {

    fun findByChatRoom(chatRoom: ChatRoom): List<ChatUser>
    fun findByChatRoomAndAccount(chatRoom: ChatRoom, account: Account): List<ChatUser>

    @EntityGraph(attributePaths = ["chatRoom"])
    fun findByAccount(account: Account): List<ChatUser>
}
