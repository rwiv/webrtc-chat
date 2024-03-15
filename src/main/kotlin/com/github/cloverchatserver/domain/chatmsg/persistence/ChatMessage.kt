package com.github.cloverchatserver.domain.chatmsg.persistence

import com.github.cloverchatserver.domain.chatroom.persistence.ChatRoom
import com.github.cloverchatserver.domain.account.persistence.Account
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
    val createAt: LocalDateTime,
)
