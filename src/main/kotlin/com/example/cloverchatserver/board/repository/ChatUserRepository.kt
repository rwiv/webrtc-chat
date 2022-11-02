package com.example.cloverchatserver.board.repository

import org.springframework.data.jpa.repository.JpaRepository

interface ChatUserRepository : JpaRepository<ChatUser, Long> {
}