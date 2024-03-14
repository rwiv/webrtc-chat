package com.github.cloverchatserver.domain.chatmsg.repository

import com.github.cloverchatserver.domain.chatroom.repository.ChatRoom
import com.github.cloverchatserver.domain.chatmsg.controller.domain.ResponseStompChatMessage
import com.github.cloverchatserver.domain.user.repository.Account
import java.lang.RuntimeException
import java.time.LocalDateTime
import jakarta.persistence.*

@Entity
@Table(name = "chat_message")
class ChatMessage(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_message_id")
    val id: Long?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    val chatRoom: ChatRoom,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val createAccount: Account,

    @Column(length = 200, nullable = false, updatable = false)
    val content: String,

    @Column(nullable = false, updatable = false)
    val createAt: LocalDateTime
) {

    fun toResponseStompChatMessage(): ResponseStompChatMessage {
        if (id == null) {
            throw RuntimeException("id is null")
        }

        return ResponseStompChatMessage(
            id!!,
            chatRoom.toResponseChatRoom(),
            createAccount.toResponseUser(),
            content,
            createAt
        )
    }
}