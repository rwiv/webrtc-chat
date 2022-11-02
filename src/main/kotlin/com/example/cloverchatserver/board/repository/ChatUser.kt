package com.example.cloverchatserver.board.repository

import com.example.cloverchatserver.board.controller.ResponseChatUser
import com.example.cloverchatserver.user.repository.User
import javax.persistence.*

@Entity
@Table(name = "chat_user")
class ChatUser(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_user_id")
    val id: Long?,

    @ManyToOne
    @JoinColumn(name = "chat_room_id")
    val chatRoom: ChatRoom,

    @ManyToOne
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