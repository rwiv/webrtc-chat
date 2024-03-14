package com.github.cloverchatserver.domain.chatuser.controller

import com.github.cloverchatserver.domain.chatuser.controller.domain.ResponseChatUser
import com.github.cloverchatserver.domain.chatuser.service.ChatUserService
import com.github.cloverchatserver.domain.user.controller.domain.ResponseUser
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/chat/user")
class ChatUserHttpController(val chatUserService: ChatUserService) {

    @GetMapping("/list/{chatRoomId}")
    fun getChatUsersByChatRoomId(@PathVariable chatRoomId: Long, authentication: Authentication): List<ResponseChatUser> {
        val responseUser = authentication.details as ResponseUser

        return chatUserService.getChatUsersByChatRoomIdAssertUser(chatRoomId, responseUser)
            .map { chatUser -> chatUser.toResponseChatUser() }
    }

//    @PostMapping("/create/{chatRoomId}")
//    fun createChatUser(@PathVariable chatRoomId: Long, @RequestParam sessionId: String, authentication: Authentication): ResponseChatUser {
//        val responseUser = authentication.details as ResponseUser
//
//        return chatUserService.createChatUser(chatRoomId, responseUser, sessionId)
//            .toResponseChatUser()
//    }
//
//    @DeleteMapping("/delete")
//    fun deleteChatUserByUserId(authentication: Authentication): List<ResponseChatUser> {
//        val responseUser = authentication.details as ResponseUser
//
//        return chatUserService.deleteChatUserByUserId(responseUser)
//            .map { chatUser -> chatUser.toResponseChatUser() }
//    }
//
//    @DeleteMapping("/delete/{chatRoomId}")
//    fun deleteChatUserByUserIdAndChatRoomId(@PathVariable chatRoomId: Long, authentication: Authentication): List<ResponseChatUser> {
//        val responseUser = authentication.details as ResponseUser
//
//        return chatUserService.deleteChatUserByUserIdAndChatRoomId(chatRoomId, responseUser)
//            .map { chatUser -> chatUser.toResponseChatUser() }
//    }
}