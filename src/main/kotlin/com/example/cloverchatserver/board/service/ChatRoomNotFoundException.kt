package com.example.cloverchatserver.board.service

class ChatRoomNotFoundException(
    message: String = "ChatRoom Not Found!"
) : RuntimeException(message) {}