package com.github.cloverchatserver.domain.chatmsg.api

import com.github.cloverchatserver.domain.chatmsg.api.data.StompChatMessage
import com.github.cloverchatserver.domain.chatuser.business.ChatUserService
import com.github.cloverchatserver.security.authentication.AuthenticationToken
import com.github.cloverchatserver.domain.account.business.data.AccountResponse
import com.github.cloverchatserver.domain.chatmsg.business.ChatMessageService
import com.github.cloverchatserver.domain.chatmsg.business.data.ChatMessageCreation
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller
import java.lang.RuntimeException
import java.security.Principal

//@Controller
class ChatMessageStompController(
    val chatMessageService: ChatMessageService,
    val chatUserService: ChatUserService,
    val template: SimpMessagingTemplate,
) {

//    @MessageMapping("/message/{chatRoomId}")
//    fun chatMessageHandle(
//        stompChatMessage: StompChatMessage,
//        authentication: Principal,
//        @DestinationVariable chatRoomId: Long
//    ) {
//        val accountResponse: AccountResponse = (authentication as AuthenticationToken).details as AccountResponse
//        val chatUsers = chatUserService.getChatUsersByChatRoomId(chatRoomId, accountResponse)
//
//        chatUsers.forEach { chatUser ->
//            template.convertAndSendToUser(
//                chatUser.account.username,
//                "/sub/message/$chatRoomId",
//                stompChatMessage
//            )
//        }
//    }

//    @MessageMapping("/message/{chatRoomId}")
//    fun chatMessageHandle(
//        chatMessageCreation: ChatMessageCreation,
//        authentication: Principal,
//        @DestinationVariable chatRoomId: Long
//    ) {
//        if (chatMessageCreation.chatRoomId != chatRoomId) {
//            throw RuntimeException("chatRoomId is different")
//        }
//
//        val accountResponse: AccountResponse = (authentication as AuthenticationToken).details as AccountResponse
//
//        val chatMessage = chatMessageService.createChatMessage(chatMessageCreation, accountResponse)
//        val stompChatMessage = StompChatMessage.of(chatMessage)
//
//        val chatUsers = chatUserService.getChatUsersByChatRoomId(chatRoomId, accountResponse)
//        chatUsers.forEach { chatUser ->
//            template.convertAndSendToUser(
//                chatUser.account.username,
//                "/sub/message/$chatRoomId",
//                stompChatMessage,
//            )
//        }
//    }
}