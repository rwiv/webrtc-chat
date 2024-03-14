package com.example.cloverchatserver.domain.chatroom.controller

import com.example.cloverchatserver.domain.chatroom.controller.domain.StompUpdateChatRoom
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller

@Controller
class ChatRoomStompController{

    @MessageMapping("/room")
    @SendTo("/sub/room")
    fun chatRoomHandle(stompUpdateChatRoom: StompUpdateChatRoom) = stompUpdateChatRoom
}