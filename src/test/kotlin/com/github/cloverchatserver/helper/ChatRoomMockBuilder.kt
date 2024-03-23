package com.github.cloverchatserver.helper

import com.github.cloverchatserver.domain.chatroom.business.data.ChatRoomCreation
import com.github.cloverchatserver.domain.chatroom.persistence.ChatRoomType

fun cr(accountId: Long, title: String) = ChatRoomCreation(
    createUserId = accountId,
    password = null,
    title = title,
    type = ChatRoomType.PUBLIC,
)
