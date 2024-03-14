package com.example.cloverchatserver.domain.chatuser.repository

import com.example.cloverchatserver.domain.chatuser.controller.domain.ResponseChatUser
import com.example.cloverchatserver.domain.chatroom.repository.ChatRoom
import com.example.cloverchatserver.domain.user.repository.User
import javax.persistence.*

@Entity
@Table(name = "chat_user")
class ChatUser(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_user_id")
    val id: Long?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    val chatRoom: ChatRoom,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User,

    @Column(unique = true, nullable = false, updatable = false, length = 100)
    val sessionId: String
) {

    fun toResponseChatUser(): ResponseChatUser {
        if (id == null) {
            throw RuntimeException("id is null")
        }

        return ResponseChatUser(id, chatRoom.toResponseChatRoom(), user.toResponseUser())
    }
}