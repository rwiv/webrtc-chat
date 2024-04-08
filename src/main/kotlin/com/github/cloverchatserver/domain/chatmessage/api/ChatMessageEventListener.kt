package com.github.cloverchatserver.domain.chatmessage.api

import com.github.cloverchatserver.domain.chatmessage.api.data.ChatMessageResponse
import com.github.cloverchatserver.domain.chatmessage.api.event.ChatMessageCreationEvent
import org.springframework.context.event.EventListener
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class ChatMessageEventListener(
    private val template: SimpMessagingTemplate,
) {

    @Async
    @EventListener
    fun handleReadEvent(event: ChatMessageCreationEvent) {
        val chatRoomId = event.chatMessage.chatRoom.id!!
        val msg = ChatMessageResponse.of(event.chatMessage)
        template.convertAndSend("/sub/message/${chatRoomId}", msg)
    }
}
