package com.example.cloverchatserver.chat.user.controller

import com.example.cloverchatserver.chat.user.controller.domain.StompUpdateChatUser
import com.example.cloverchatserver.chat.user.service.ChatUserService
import com.example.cloverchatserver.security.authentication.AuthenticationToken
import com.example.cloverchatserver.user.controller.domain.ResponseUser
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.security.access.AccessDeniedException
import org.springframework.stereotype.Controller
import java.security.Principal

@Controller
class ChatUserStompController(
    val chatUserService: ChatUserService,
    val template: SimpMessagingTemplate
) {

    @MessageMapping("/user/{chatRoomId}")
    fun chatUserHandle(stompUpdateChatUser: StompUpdateChatUser,
                       authentication: Principal,
                       @DestinationVariable chatRoomId: Long) {

        val responseUser: ResponseUser = (authentication as AuthenticationToken).details as ResponseUser

        val chatUsers = chatUserService.getChatUsersByChatRoomIdNotException(chatRoomId, responseUser)

        chatUsers.forEach { chatUser ->
            template.convertAndSendToUser(
                chatUser.user.email,
                "/sub/user/$chatRoomId",
                stompUpdateChatUser
            )
        }
    }
}