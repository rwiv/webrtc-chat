package com.github.cloverchatserver.domain.chatmsg.api

import com.github.cloverchatserver.domain.chatmsg.api.domain.ResponseStompChatMessage
import com.github.cloverchatserver.domain.chatuser.business.ChatUserService
import com.github.cloverchatserver.security.authentication.AuthenticationToken
import com.github.cloverchatserver.domain.account.api.domain.ResponseUser
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller
import java.security.Principal

@Controller
class ChatMessageStompController(
    val chatUserService: ChatUserService,
    val template: SimpMessagingTemplate
) {

    @MessageMapping("/message/{chatRoomId}")
    fun chatMessageHandle(
        responseStompChatMessage: ResponseStompChatMessage,
        authentication: Principal,
        @DestinationVariable chatRoomId: Long
    ) {
        val responseUser: ResponseUser = (authentication as AuthenticationToken).details as ResponseUser
        val chatUsers = chatUserService.getChatUsersByChatRoomId(chatRoomId, responseUser)

        chatUsers.forEach { chatUser ->
            template.convertAndSendToUser(
                chatUser.account.username,
                "/sub/message/$chatRoomId",
                responseStompChatMessage
            )
        }
    }
}