package com.github.cloverchatserver.domain.chatmsg.api

import com.github.cloverchatserver.domain.account.business.data.AccountResponse
import com.github.cloverchatserver.domain.chatmsg.api.data.ChatMessageCreateRequest
import com.github.cloverchatserver.domain.chatmsg.api.data.StompChatMessage
import com.github.cloverchatserver.domain.chatmsg.api.event.ChatMessageCreationEvent
import com.github.cloverchatserver.domain.chatmsg.business.ChatMessageService
import com.github.cloverchatserver.domain.chatmsg.business.data.ChatMessageCreation
import org.springframework.context.ApplicationEventPublisher
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/chat-messages")
class ChatMessageHttpController(
    private val chatMessageService: ChatMessageService,
    private val publisher: ApplicationEventPublisher,
) {

    @PostMapping("/{chatRoomId}")
    fun createChatMessage(
        @PathVariable chatRoomId: Long,
        @RequestBody req: ChatMessageCreateRequest,
        authentication: Authentication
    ): StompChatMessage {
        val accountResponse = authentication.details as AccountResponse

        val creation = ChatMessageCreation(chatRoomId, accountResponse.id, req.content)
        val chatMessage = chatMessageService.create(creation)
        val stompMessage = StompChatMessage.of(chatMessage)

        publisher.publishEvent(ChatMessageCreationEvent(chatMessage))

        return stompMessage
    }
}
