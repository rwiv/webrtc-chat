package com.github.cloverchatserver.domain.chatroom.repository

import org.springframework.data.jpa.repository.JpaRepository

interface ChatRoomRepository : JpaRepository<ChatRoom, Long> {
}