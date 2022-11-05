package com.example.cloverchatserver.board.controller

import com.example.cloverchatserver.board.controller.domain.RequestChatRoomCreateForm
import com.example.cloverchatserver.board.controller.domain.ResponseChatRoom
import com.example.cloverchatserver.board.service.ChatRoomService
import com.example.cloverchatserver.user.controller.domain.ResponseUser
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/board/chatroom")
class ChatRoomController(
    val chatRoomService: ChatRoomService
) {

    @GetMapping("/list")
    fun getChatRoomList(): List<ResponseChatRoom> =
        chatRoomService.getChatRoomList()
            .map { chatRoom -> chatRoom.toResponseChatRoom() }

    @PostMapping("/create")
    fun createChatRoom(@RequestBody requestChatRoomCreateForm: RequestChatRoomCreateForm): ResponseEntity<ResponseChatRoom> {
        val chatRoom = chatRoomService.createChatRoom(requestChatRoomCreateForm)

        return ResponseEntity.ok().body(chatRoom.toResponseChatRoom())
    }

    @DeleteMapping("/delete")
    fun removeChatRoom(@RequestParam chatRoomId: Long, authentication: Authentication): ResponseEntity<ResponseChatRoom> {
        val responseUser = authentication.details as ResponseUser
        val chatRoom = chatRoomService.deleteChatRoom(chatRoomId, responseUser)

        return ResponseEntity.ok().body(chatRoom.toResponseChatRoom())
    }
}