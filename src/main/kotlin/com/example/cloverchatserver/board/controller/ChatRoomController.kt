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
    fun getChatRoomList(): List<ResponseChatRoom> = chatRoomService.getChatRoomList()

    @PostMapping("/create")
    fun createChatRoom(@RequestBody requestChatRoomCreateForm: RequestChatRoomCreateForm): ResponseEntity<String> {
        chatRoomService.createChatRoom(requestChatRoomCreateForm)

        return ResponseEntity.ok().body("ok")
    }

    @DeleteMapping("/delete")
    fun removeChatRoom(@RequestParam chatRoomId: Long, authentication: Authentication): ResponseEntity<String> {
        val responseUser = authentication.details as ResponseUser
        chatRoomService.deleteChatRoom(chatRoomId, responseUser)

        return ResponseEntity.ok().body("ok")
    }
}