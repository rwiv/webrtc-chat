package com.github.cloverchatserver.domain.chatmsg.persistence

import com.github.cloverchatserver.domain.chatroom.persistence.ChatRoom
import com.github.cloverchatserver.domain.account.persistence.Account
import java.time.LocalDateTime
import jakarta.persistence.*

@Entity
@Table(name = "chat_message", indexes = [
//    Index(name = "idx_chat_room_id_num", columnList = "chat_room_id, num", unique = true),
//    Index(name = "idx_chat_room_id_num_desc", columnList = "chat_room_id, num DESC", unique = true),
    Index(name = "idx_chat_room_id_num_asc", columnList = "chat_room_id, num"),
    Index(name = "idx_chat_room_id_num_desc", columnList = "chat_room_id, num DESC"),
])
class ChatMessage(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_message_id")
    val id: Long?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    val chatRoom: ChatRoom,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val createdBy: Account,

    @Column(length = 200, nullable = false, updatable = false)
    val content: String,

    @Column(nullable = false, updatable = false)
    val createdAt: LocalDateTime,

    @Column
    val num: Int,
) {
    @Column(unique = true)
    private val crIdNum: String = "${chatRoom.id}-${num}"
}
