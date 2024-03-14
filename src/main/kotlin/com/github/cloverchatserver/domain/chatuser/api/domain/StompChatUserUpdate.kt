package com.github.cloverchatserver.domain.chatuser.api.domain

import com.github.cloverchatserver.common.websocket.WebsocketAction
import com.github.cloverchatserver.domain.chatuser.business.data.ChatUserDto

data class StompChatUserUpdate(
    val action: WebsocketAction,
    val chatUser: ChatUserDto
)