package com.github.cloverchatserver.domain.chatroom.business

class ChatRoomNotFoundException(
    message: String = "ChatRoom Not Found!"
) : RuntimeException(message) {}