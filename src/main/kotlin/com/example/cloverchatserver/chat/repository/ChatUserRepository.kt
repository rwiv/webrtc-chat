package com.example.cloverchatserver.chat.repository

import org.springframework.data.jpa.repository.JpaRepository

interface ChatUserRepository : JpaRepository<ChatUser, Long> {
}