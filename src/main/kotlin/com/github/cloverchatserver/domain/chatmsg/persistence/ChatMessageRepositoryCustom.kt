package com.github.cloverchatserver.domain.chatmsg.persistence

import com.github.cloverchatserver.domain.chatroom.persistence.ChatRoom

interface ChatMessageRepositoryCustom {

    fun findByDescOffset(chatRoom: ChatRoom, page: Int, size: Int, offset: Int): List<ChatMessage>
}