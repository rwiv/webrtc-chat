package com.github.cloverchatserver.domain.friend.persistence

import com.github.cloverchatserver.domain.account.persistence.Account
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository

interface FriendRepository : JpaRepository<Friend, Long> {

    @EntityGraph(attributePaths = ["from"])
    fun findByFrom(from: Account): List<Friend>
}
