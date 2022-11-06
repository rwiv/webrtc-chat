package com.example.cloverchatserver.chat.user.repository

import com.example.cloverchatserver.chat.user.controller.domain.ResponseChatUser
import com.example.cloverchatserver.board.repository.ChatRoom
import com.example.cloverchatserver.user.repository.User
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
    val user: User

) {

    fun toResponseChatUser(): ResponseChatUser {
        if (id == null) {
            throw RuntimeException("id is null")
        }

        return ResponseChatUser(id, chatRoom.toResponseChatRoom(), user.toResponseUser())
    }
}