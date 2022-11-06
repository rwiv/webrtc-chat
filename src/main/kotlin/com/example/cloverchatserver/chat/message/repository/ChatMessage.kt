package com.example.cloverchatserver.chat.message.repository

import com.example.cloverchatserver.board.repository.ChatRoom
import com.example.cloverchatserver.chat.message.controller.domain.ResponseStompChatMessage
import com.example.cloverchatserver.user.repository.User
import java.lang.RuntimeException
import java.time.LocalDateTime
import javax.persistence.*

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
    val createUser: User,

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
            id,
            chatRoom.toResponseChatRoom(),
            createUser.toResponseUser(),
            content,
            createAt
        )
    }
}