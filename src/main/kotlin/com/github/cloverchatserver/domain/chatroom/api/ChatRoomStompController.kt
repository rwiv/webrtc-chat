package com.github.cloverchatserver.domain.chatroom.api

import com.github.cloverchatserver.domain.chatroom.api.stomp.StompChatRoomUpdate
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller

@Controller
class ChatRoomStompController{

    @MessageMapping("/room")
    @SendTo("/sub/room")
    fun chatRoomHandle(stompChatRoomUpdate: StompChatRoomUpdate) = stompChatRoomUpdate
}