package com.github.cloverchatserver.domain.chatroom.persistence

import com.github.cloverchatserver.domain.account.persistence.Account
import com.github.cloverchatserver.domain.chatmessage.persistence.ChatMessage
import com.github.cloverchatserver.domain.chatuser.persistence.ChatUser
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "chat_room")
class ChatRoom(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_id")
    val id: Long?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val createdBy: Account,

    @Column(length = 20, nullable = true, updatable = false)
    val password: String?,

    @Column(nullable = false)
    val title: String,

    @Column(nullable = false, updatable = false)
    val createdAt: LocalDateTime,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val type: ChatRoomType,

    @Column(nullable = false)
    var chatUserCnt: Int = 0,
) {

    @Column(nullable = false)
    val hasPassword = password !== null

    @OneToMany(mappedBy = "chatRoom", fetch = FetchType.LAZY, cascade = [ CascadeType.REMOVE ])
    val chatMessages: MutableList<ChatMessage> = ArrayList()

    @OneToMany(mappedBy = "chatRoom", fetch = FetchType.LAZY, cascade = [ CascadeType.REMOVE ])
    val chatUsers: MutableList<ChatUser> = ArrayList()
}
