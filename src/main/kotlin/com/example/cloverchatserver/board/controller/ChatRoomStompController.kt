package com.example.cloverchatserver.board.controller

import com.example.cloverchatserver.board.controller.domain.RequestChatRoomCreateForm
import com.example.cloverchatserver.board.service.ChatRoomService
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller

@Controller
class ChatRoomStompController(val chatRoomService: ChatRoomService) {

    @MessageMapping("/room")
    @SendTo("/sub/room")
    fun chatRoomHandle(form: RequestChatRoomCreateForm) =
        chatRoomService.createChatRoom(form).toResponseChatRoom()
}