package com.github.cloverchatserver.domain.chatmsg.api

import com.github.cloverchatserver.domain.chatmsg.business.data.ChatMessagesFindForm
import com.github.cloverchatserver.domain.chatmsg.business.data.ChatMessageCreation
import com.github.cloverchatserver.domain.chatmsg.api.data.StompChatMessage
import com.github.cloverchatserver.domain.chatmsg.business.ChatMessageService
import com.github.cloverchatserver.domain.account.business.data.AccountResponse
import com.github.cloverchatserver.domain.chatuser.business.ChatUserService
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import java.lang.RuntimeException

@RestController
@RequestMapping("/chat/message")
class ChatMessageHttpController(
    private val chatMessageService: ChatMessageService,
    private val chatUserService: ChatUserService,
    private val template: SimpMessagingTemplate,
) {

    @PostMapping("/list")
    fun getChatMessages(@ModelAttribute form: ChatMessagesFindForm): List<StompChatMessage> {
        return chatMessageService
            .findByForm(form)
            .map { chatMessage -> StompChatMessage.of(chatMessage) }
    }

    @PostMapping("/create/{chatRoomId}")
    fun createChatMessage(
        @PathVariable chatRoomId: Long,
        @RequestBody chatMessageCreation: ChatMessageCreation,
        authentication: Authentication
    ): StompChatMessage {
        if (chatMessageCreation.chatRoomId != chatRoomId) {
            throw RuntimeException("chatRoomId is different")
        }
        val accountResponse = authentication.details as AccountResponse

        val chatMessage = chatMessageService.createChatMessage(chatMessageCreation, accountResponse)
        val stompMessage = StompChatMessage.of(chatMessage)

        val chatUsers = chatUserService.findByChatRoomId(chatRoomId)

        chatUsers.forEach { chatUser ->
            template.convertAndSendToUser(
                chatUser.account.username,
                "/sub/message/$chatRoomId",
                stompMessage,
            )
        }
        return stompMessage
    }
}
