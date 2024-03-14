package com.github.cloverchatserver.domain.user.service

import com.github.cloverchatserver.domain.user.controller.domain.RequestUserCreateForm
import com.github.cloverchatserver.domain.user.repository.Account
import com.github.cloverchatserver.domain.user.repository.AccountRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AccountServiceImpl(

    private val accountRepository: AccountRepository,
    private val passwordEncoder: PasswordEncoder

) : AccountService {

    @Transactional
    override fun createUser(requestUserCreateForm: RequestUserCreateForm): Account {
        val user = requestUserCreateForm.toUser(passwordEncoder)

        return accountRepository.save(user)
    }

    @Transactional
    override fun getUserBy(userId: Long): Account? {
        return accountRepository.findById(userId)
            .orElseGet { null }
    }

    override fun findByUsername(username: String): Account? {
        return accountRepository.findByUsername(username)
    }
}