package com.github.cloverchatserver.common.websocket

import com.github.cloverchatserver.domain.chatuser.business.data.ChatUserDto
import com.github.cloverchatserver.domain.chatuser.api.domain.StompChatUserUpdate
import com.github.cloverchatserver.domain.chatuser.business.ChatUserService
import com.github.cloverchatserver.security.authentication.AuthenticationToken
import com.github.cloverchatserver.domain.account.business.data.AccountResponse
import org.springframework.context.event.EventListener
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Component
import org.springframework.web.socket.messaging.SessionDisconnectEvent
import org.springframework.web.socket.messaging.SessionSubscribeEvent

@Component
class WebSocketListener(
    val chatUserService: ChatUserService,
    val template: SimpMessagingTemplate
) {

    companion object {
        const val SESSION_DEST_KEY = "simpDestination"
        const val SESSION_ID_KEY = "simpSessionId"
    }

    @EventListener
    fun handleSessionDisconnect(event: SessionDisconnectEvent) {
        val headers = event.message.headers

        val accountResponse = (event.user as AuthenticationToken).details as AccountResponse
        val sessionId = headers[SESSION_ID_KEY] as String

        try {
            val chatUser = chatUserService.deleteChatUserBySessionId(sessionId, accountResponse)
            broadcastMessage(WebsocketAction.DELETE, ChatUserDto.of(chatUser), chatUser.chatRoom.id!!)
        } catch (_: RuntimeException) {}
    }

    @EventListener
    fun handleSessionSubscribe(event: SessionSubscribeEvent) {
        val headers = event.message.headers
        val dest: String = headers[SESSION_DEST_KEY] as String

        val chunks = dest.split("/")
        if (chunks[2] != "message") {
            return
        }

        val chatRoomId = chunks[3].toLong()
        val accountResponse = (event.user as AuthenticationToken).details as AccountResponse
        val sessionId = headers[SESSION_ID_KEY] as String

        try {
            val chatUser = chatUserService.createChatUser(chatRoomId, accountResponse, sessionId)
            broadcastMessage(WebsocketAction.CREATE, ChatUserDto.of(chatUser), chatUser.chatRoom.id!!)
        } catch (_: RuntimeException) {}
    }

    fun broadcastMessage(action: WebsocketAction, chatUserDto: ChatUserDto, chatRoomId: Long) {
        val stompChatUserUpdate = StompChatUserUpdate(action, chatUserDto)
        val chatUsers = chatUserService.findByChatRoomId(chatRoomId)

        chatUsers.forEach { chatUser ->
            template.convertAndSendToUser(
                chatUser.account.username,
                "/sub/user/$chatRoomId",
                stompChatUserUpdate
            )
        }
    }
}