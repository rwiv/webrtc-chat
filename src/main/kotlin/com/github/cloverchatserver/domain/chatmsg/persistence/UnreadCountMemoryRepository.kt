package com.github.cloverchatserver.domain.chatmsg.persistence

import org.springframework.stereotype.Component

@Component
class UnreadCountMemoryRepository {

    private val map: MutableMap<Long, Int> = HashMap()

    fun set(chatMessageId: Long, count: Int) {
        map[chatMessageId] = count
    }

    fun get(chatMessageId: Long): Int? {
        return map[chatMessageId]
    }
}
