package com.github.cloverchatserver.domain.account.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository : JpaRepository<Account, Long> {

    fun findByUsername(username: String): Account?
}