package com.example.cloverchatserver.domain.chatmsg.controller

import com.example.cloverchatserver.domain.chatmsg.controller.domain.ResponseStompChatMessage
import com.example.cloverchatserver.domain.chatuser.service.ChatUserService
import com.example.cloverchatserver.security.authentication.AuthenticationToken
import com.example.cloverchatserver.domain.user.controller.domain.ResponseUser
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
    fun chatMessageHandle(responseStompChatMessage: ResponseStompChatMessage,
                          authentication: Principal,
                          @DestinationVariable chatRoomId: Long) {

        val responseUser: ResponseUser = (authentication as AuthenticationToken).details as ResponseUser
        val chatUsers = chatUserService.getChatUsersByChatRoomId(chatRoomId, responseUser)

        chatUsers.forEach { chatUser ->
            template.convertAndSendToUser(
                chatUser.user.email,
                "/sub/message/$chatRoomId",
                responseStompChatMessage
            )
        }
    }
}