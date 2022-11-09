package com.example.cloverchatserver.chat.user.controller

import com.example.cloverchatserver.chat.user.controller.domain.ResponseChatUser
import com.example.cloverchatserver.chat.user.service.ChatUserService
import com.example.cloverchatserver.user.controller.domain.ResponseUser
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller

@Controller
class ChatUserStompController(val chatUserService: ChatUserService) {

    @MessageMapping("/user/{chatRoomId}")
    @SendTo("/sub/user/{chatRoomId}")
    fun chatUserHandle(responseUser: ResponseUser,
                          @DestinationVariable chatRoomId: Long): ResponseChatUser {

        return chatUserService.createChatUser(chatRoomId, responseUser)
            .toResponseChatUser()
    }
}