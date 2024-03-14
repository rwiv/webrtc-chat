package com.github.cloverchatserver.domain.account.business

import com.github.cloverchatserver.domain.account.business.data.RequestUserCreateForm
import com.github.cloverchatserver.domain.account.persistence.Account
import com.github.cloverchatserver.domain.account.persistence.AccountRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull

@Service
class AccountService(
    private val accountRepository: AccountRepository,
    private val passwordEncoder: PasswordEncoder
) {
    @Transactional
    fun create(requestUserCreateForm: RequestUserCreateForm): Account {
        val user = requestUserCreateForm.toUser(passwordEncoder)

        return accountRepository.save(user)
    }

    @Transactional
    fun findById(id: Long): Account? {
        return accountRepository.findById(id).getOrNull()
    }

    @Transactional
    fun findByUsername(username: String): Account? {
        return accountRepository.findByUsername(username)
    }
}