package com.github.cloverchatserver.domain.user.service

import com.github.cloverchatserver.domain.user.controller.domain.RequestUserCreateForm
import com.github.cloverchatserver.domain.user.repository.User
import com.github.cloverchatserver.domain.user.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(

    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder

) : UserService {

    @Transactional
    override fun createUser(requestUserCreateForm: RequestUserCreateForm): User {
        val user = requestUserCreateForm.toUser(passwordEncoder)

        return userRepository.save(user)
    }

    @Transactional
    override fun getUserBy(userId: Long): User? {
        return userRepository.findById(userId)
            .orElseGet { null }
    }
}