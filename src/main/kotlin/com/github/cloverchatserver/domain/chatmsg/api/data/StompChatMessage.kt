package com.github.cloverchatserver.domain.chatmsg.api.data

import com.github.cloverchatserver.domain.chatmsg.persistence.ChatMessage
import java.io.Serializable

data class StompChatMessage(
    val id: Long,
    val num: Int,
) : Serializable {

    companion object {
        fun of(chatMessage: ChatMessage) = StompChatMessage(
            chatMessage.id!!,
            chatMessage.num,
        )
    }
}
