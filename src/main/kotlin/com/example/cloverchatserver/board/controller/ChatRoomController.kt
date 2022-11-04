package com.example.cloverchatserver.board.controller

import com.example.cloverchatserver.board.controller.domain.ChatRoomCreateForm
import com.example.cloverchatserver.board.controller.domain.ResponseChatRoom
import com.example.cloverchatserver.board.service.ChatRoomService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/board")
class ChatRoomController(
    val chatRoomService: ChatRoomService
) {

    @GetMapping
    fun hello(): String = "hello"

    @GetMapping("/list")
    fun getChatRoomList(): List<ResponseChatRoom> = chatRoomService.getChatRoomList()

    @PostMapping("/create")
    fun createChatRoom(@RequestBody chatRoomCreateForm: ChatRoomCreateForm): ResponseEntity<String> {
        chatRoomService.createChatRoom(chatRoomCreateForm)

        return ResponseEntity.ok().body("ok")
    }

    @DeleteMapping("/delete")
    fun removeChatRoom(@RequestParam chatRoomId: Long, @RequestParam password: String): ResponseEntity<String> {
        chatRoomService.deleteChatRoom(chatRoomId, password)

        return ResponseEntity.ok().body("ok")
    }
}