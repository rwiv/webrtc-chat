package com.example.cloverchatserver.board.controller

import com.example.cloverchatserver.board.controller.domain.ResponseChatRoom
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller

@Controller
class ChatRoomStompController{

    @MessageMapping("/room")
    @SendTo("/sub/room")
    fun chatRoomHandle(responseChatRoom: ResponseChatRoom) = responseChatRoom
}