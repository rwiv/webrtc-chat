package com.github.cloverchatserver.domain.chatmessage.persistence

import com.github.cloverchatserver.domain.chatroom.persistence.ChatRoom
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository

@Repository
class ChatMessageRepositoryCustomImpl(
    private val em: EntityManager,
) : ChatMessageRepositoryCustom {

    override fun findByDescOffset(chatRoom: ChatRoom, page: Int, size: Int, offset: Int): List<ChatMessage> {
        val off = (page * size) + offset
        val query = em.createQuery(
            "SELECT cm FROM ChatMessage cm " +
                    "WHERE cm.chatRoom.id = ${chatRoom.id} " +
                    "ORDER BY cm.num DESC " +
                    "LIMIT $size OFFSET $off",
            ChatMessage::class.java
        )

        return query.resultList
    }
}