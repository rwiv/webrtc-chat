package com.github.cloverchatserver.domain.account.persistence

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository : JpaRepository<Account, Long> {

    fun findByUsername(username: String): Account?
    fun findAllBy(pageable: Pageable): Page<Account>
}