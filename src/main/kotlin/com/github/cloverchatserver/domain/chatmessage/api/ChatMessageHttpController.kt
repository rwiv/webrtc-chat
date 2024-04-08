package com.github.cloverchatserver.domain.chatmessage.api

import com.github.cloverchatserver.domain.account.business.data.AccountResponse
import com.github.cloverchatserver.domain.chatmessage.api.data.ChatMessageCreateRequest
import com.github.cloverchatserver.domain.chatmessage.api.data.ChatMessageResponse
import com.github.cloverchatserver.domain.chatmessage.api.event.ChatMessageCreationEvent
import com.github.cloverchatserver.domain.chatmessage.business.ChatMessageService
import com.github.cloverchatserver.domain.chatmessage.business.data.ChatMessageCreation
import org.springframework.context.ApplicationEventPublisher
import org.springframework.http.ResponseEntity
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
    ): ResponseEntity<ChatMessageResponse> {
        val accountResponse = authentication.details as AccountResponse

        val creation = ChatMessageCreation(chatRoomId, accountResponse.id, req.content)
        val chatMessage = chatMessageService.create(creation)

        publisher.publishEvent(ChatMessageCreationEvent(chatMessage))

        return ResponseEntity.ok(ChatMessageResponse.of(chatMessage))
    }
}
