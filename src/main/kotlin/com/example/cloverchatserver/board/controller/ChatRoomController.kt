package com.example.cloverchatserver.board.controller

import com.example.cloverchatserver.board.repository.ChatRoomRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.lang.RuntimeException

@RestController
class ChatRoomController(
    val chatRoomRepository: ChatRoomRepository
) {

    @GetMapping
    fun hello(): String = "hello"

    @GetMapping("/list")
    fun getChatRoomList(): List<ResponseChatRoom> = chatRoomRepository.findAll().map { p -> p.toResponseChatRoom() }

    @PostMapping("/create")
    fun createChatRoom(@RequestBody requestChatRoom: RequestChatRoom): ResponseEntity<String> {
        val chatRoom = requestChatRoom.toChatRoom()
        chatRoomRepository.save(chatRoom)

        return ResponseEntity.ok().body("ok")
    }

    @DeleteMapping("/delete")
    fun removeChatRoom(@RequestParam postId: Long, @RequestParam password: String): ResponseEntity<String> {
        val chatRoom = chatRoomRepository
            .findById(postId)
            .orElseThrow { throw RuntimeException("ChatRoom Not Found!") }

        if (chatRoom.password != password) {
            throw RuntimeException("비밀번호가 다릅니다")
        }

        chatRoomRepository.delete(chatRoom)

        return ResponseEntity.ok().body("ok")
    }
}