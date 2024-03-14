package com.github.cloverchatserver.configures

import com.github.cloverchatserver.domain.chatuser.controller.domain.ResponseChatUser
import com.github.cloverchatserver.domain.chatuser.controller.domain.StompUpdateChatUser
import com.github.cloverchatserver.domain.chatuser.service.ChatUserService
import com.github.cloverchatserver.common.MethodType
import com.github.cloverchatserver.security.authentication.AuthenticationToken
import com.github.cloverchatserver.domain.user.controller.domain.ResponseUser
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

        val responseUser = (event.user as AuthenticationToken).details as ResponseUser
        val sessionId = headers[SESSION_ID_KEY] as String

        try {
            val chatUser = chatUserService.deleteChatUserBySessionId(sessionId, responseUser)

            broadcastMessage(MethodType.DELETE, chatUser.toResponseChatUser(), chatUser.chatRoom.id!!, responseUser)
        } catch (_: RuntimeException) {}
    }

    @EventListener
    fun handleSessionSubscribe(event: SessionSubscribeEvent) {
        val headers = event.message.headers
        val dest: String = headers[SESSION_DEST_KEY] as String

        val chunks = PathHelper.getChunks(dest)
        if (!PathHelper.isMessageSession(chunks)) return

        val chatRoomId = chunks[3].toLong()
        val responseUser = (event.user as AuthenticationToken).details as ResponseUser
        val sessionId = headers[SESSION_ID_KEY] as String

        try {
            val chatUser = chatUserService.createChatUser(chatRoomId, responseUser, sessionId)

            broadcastMessage(MethodType.CREATE, chatUser.toResponseChatUser(), chatUser.chatRoom.id!!, responseUser)
        } catch (_: RuntimeException) {}
    }

    fun broadcastMessage(type: MethodType, responseChatUser: ResponseChatUser, chatRoomId: Long, responseUser: ResponseUser) {
        val stompUpdateChatUser = StompUpdateChatUser(type, responseChatUser)
        val chatUsers = chatUserService.getChatUsersByChatRoomId(chatRoomId, responseUser)

        chatUsers.forEach { chatUser ->
            template.convertAndSendToUser(
                chatUser.user.email,
                "/sub/user/$chatRoomId",
                stompUpdateChatUser
            )
        }
    }
}