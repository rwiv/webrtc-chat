package com.github.cloverchatserver.domain.chatroom.service

class ChatRoomNotFoundException(
    message: String = "ChatRoom Not Found!"
) : RuntimeException(message) {}