package com.example.cloverchatserver.chat.user.controller

import com.example.cloverchatserver.chat.user.controller.domain.ResponseChatUser
import com.example.cloverchatserver.chat.user.service.ChatUserService
import com.example.cloverchatserver.user.controller.domain.ResponseUser
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/chat/user")
class ChatUserController(
    val chatUserService: ChatUserService
) {

    @GetMapping("/list/{chatRoomId}")
    fun getChatUsersByChatRoomId(@PathVariable chatRoomId: Long, authentication: Authentication): List<ResponseChatUser> {
        val responseUser = authentication.details as ResponseUser

        return chatUserService.getChatUsersByChatRoomId(chatRoomId, responseUser)
    }

    @PostMapping("/create/{chatRoomId}")
    fun createChatUser(@PathVariable chatRoomId: Long, authentication: Authentication): ResponseChatUser {
        val responseUser = authentication.details as ResponseUser

        return chatUserService.createChatUser(chatRoomId, responseUser)
    }

    @DeleteMapping("/delete/{chatRoomId}")
    fun deleteChatUser(@PathVariable chatRoomId: Long, authentication: Authentication): ResponseChatUser {
        val responseUser = authentication.details as ResponseUser

        return chatUserService.deleteChatUser(chatRoomId, responseUser)
    }
}