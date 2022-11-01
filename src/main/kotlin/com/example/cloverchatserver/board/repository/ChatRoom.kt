package com.example.cloverchatserver.board.repository

import com.example.cloverchatserver.board.controller.ResponseChatRoom
import java.lang.RuntimeException
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "chat_room")
class ChatRoom(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_id")
    val id: Long?,

    @Column(nullable = false)
    val createBy: String,

    @Column(length = 20, nullable = false, updatable = false)
    val password: String,

    @Column(nullable = false)
    val title: String,

    @Column(nullable = false, updatable = false)
    val createDate: LocalDateTime

) {

    fun toResponseChatRoom(): ResponseChatRoom {
        if (id == null) throw RuntimeException()

        return ResponseChatRoom(id, createBy, title, createDate)
    }
}