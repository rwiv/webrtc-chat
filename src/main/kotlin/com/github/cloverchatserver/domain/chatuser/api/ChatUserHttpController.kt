package com.github.cloverchatserver.domain.chatuser.api

import com.github.cloverchatserver.domain.chatuser.business.data.ChatUserDto
import com.github.cloverchatserver.domain.chatuser.business.ChatUserService
import com.github.cloverchatserver.domain.account.business.data.AccountResponse
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/chat/user")
class ChatUserHttpController(val chatUserService: ChatUserService) {

    @GetMapping("/list/{chatRoomId}")
    fun getChatUsersByChatRoomId(@PathVariable chatRoomId: Long, authentication: Authentication): List<ChatUserDto> {
        val accountResponse = authentication.details as AccountResponse

        return chatUserService.getChatUsersByChatRoomIdAssertUser(chatRoomId, accountResponse)
            .map { chatUser -> ChatUserDto.of(chatUser) }
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