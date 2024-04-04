package com.github.cloverchatserver.domain.account.business

import com.github.cloverchatserver.domain.account.business.data.AccountCreation
import com.github.cloverchatserver.domain.account.persistence.Account
import com.github.cloverchatserver.domain.account.persistence.AccountRepository
import org.springframework.data.domain.PageRequest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull

@Service
class AccountService(
    private val accountRepository: AccountRepository,
    private val passwordEncoder: PasswordEncoder,
) {

    @Transactional
    fun create(creation: AccountCreation): Account {
        val tbc = Account(
            null,
            creation.role,
            creation.username,
            passwordEncoder.encode(creation.password),
            creation.nickname,
            "/avatars/${getRandInt()}.svg",
        )
        return accountRepository.save(tbc)
    }

    private fun getRandInt(): Int {
        val list = ArrayList<Int>()
        for (i in 0..99) {
            list.add(i)
        }
        return list.random()
    }

    fun findAll(): List<Account> {
        return accountRepository.findAll()
    }

    fun findByPage(page: Int, size: Int): List<Account> {
        return accountRepository.findAllBy(PageRequest.of(page, size)).content
    }

    fun findById(id: Long): Account? {
        return accountRepository.findById(id).getOrNull()
    }

    fun findByUsername(username: String): Account? {
        return accountRepository.findByUsername(username)
    }
}