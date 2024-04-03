package com.github.cloverchatserver.domain.chatroom.persistence

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ChatRoomRepository : JpaRepository<ChatRoom, Long> {

    @Query("SELECT cr FROM ChatRoom cr " +
            "ORDER BY cr.createdAt DESC")
    fun findBy(pageable: Pageable): Page<ChatRoom>
}
