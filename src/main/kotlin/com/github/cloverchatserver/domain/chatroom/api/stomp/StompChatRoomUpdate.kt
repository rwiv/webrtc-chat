package com.github.cloverchatserver.domain.chatroom.api.stomp

import com.github.cloverchatserver.common.websocket.WebsocketAction
import com.github.cloverchatserver.domain.chatroom.business.data.ChatRoomDto

data class StompChatRoomUpdate(
    val action: WebsocketAction,
    val chatRoom: ChatRoomDto
)