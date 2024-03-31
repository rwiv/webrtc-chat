package com.github.cloverchatserver.domain.chatroom.persistence

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface ChatRoomRepository : JpaRepository<ChatRoom, Long> {
    fun findBy(pageable: Pageable): Page<ChatRoom>
}
