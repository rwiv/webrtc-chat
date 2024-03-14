package com.example.cloverchatserver.domain.chatroom.service

class ChatRoomNotFoundException(
    message: String = "ChatRoom Not Found!"
) : RuntimeException(message) {}