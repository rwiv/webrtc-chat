package com.example.cloverchatserver.board.repository

import com.example.cloverchatserver.board.controller.domain.ResponseChatRoom
import com.example.cloverchatserver.user.repository.User
import java.lang.RuntimeException
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "chat_room")
class ChatRoom(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_id")
    val id: Long?,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val createUser: User,

    @Column(length = 20, nullable = false, updatable = false)
    val password: String,

    @Column(nullable = false)
    val title: String,

    @Column(nullable = false, updatable = false)
    val createDate: LocalDateTime,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val type: ChatRoomType,

    ) {

    fun toResponseChatRoom(): ResponseChatRoom {
        if (id == null) throw RuntimeException()

        return ResponseChatRoom(id, createUser.toResponseUser(), title, createDate, type)
    }
}