package com.github.cloverchatserver.domain.chatroom.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface ChatRoomRepository : JpaRepository<ChatRoom, Long> {
}