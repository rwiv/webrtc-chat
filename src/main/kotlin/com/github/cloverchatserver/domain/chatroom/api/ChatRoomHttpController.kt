package com.github.cloverchatserver.domain.chatroom.api

import com.github.cloverchatserver.domain.account.business.data.AccountResponse
import com.github.cloverchatserver.domain.chatroom.api.data.ChatRoomCreateRequest
import com.github.cloverchatserver.domain.chatroom.business.ChatRoomService
import com.github.cloverchatserver.domain.chatroom.business.data.ChatRoomDto
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/board/chatroom")
class ChatRoomHttpController(val chatRoomService: ChatRoomService) {

    @GetMapping("/list")
    fun getChatRoomList(): List<ChatRoomDto> =
        chatRoomService.findAll()
            .map { chatRoom -> ChatRoomDto.of(chatRoom) }

    @PostMapping("/create")
    fun createChatRoom(@RequestBody req: ChatRoomCreateRequest, authentication: Authentication): ResponseEntity<ChatRoomDto> {
        val accountResponse = authentication.details as AccountResponse
        val creation = req.toChatRoomCreation(accountResponse.id)
        val chatRoom = chatRoomService.createChatRoom(creation)
        return ResponseEntity.ok().body(ChatRoomDto.of(chatRoom))
    }

    @DeleteMapping("/delete")
    fun removeChatRoom(@RequestParam chatRoomId: Long, authentication: Authentication): ResponseEntity<ChatRoomDto> {
        val accountResponse = authentication.details as AccountResponse
        val chatRoom = chatRoomService.deleteChatRoom(chatRoomId, accountResponse)
        return ResponseEntity.ok().body(ChatRoomDto.of(chatRoom))
    }
}