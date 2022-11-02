package com.example.cloverchatserver.user.service

import com.example.cloverchatserver.user.controller.UserCreateForm
import com.example.cloverchatserver.user.repository.User
import com.example.cloverchatserver.user.repository.UserNotFoundException
import com.example.cloverchatserver.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(

    private val userRepository: UserRepository

) : UserService {

    @Transactional
    override fun createUser(userCreateForm: UserCreateForm): User {
        val user = userCreateForm.toUser()

        return userRepository.save(user)
    }

    @Transactional
    override fun getUserBy(userId: Long): User {
        return userRepository
            .findById(userId)
            .orElseThrow { UserNotFoundException("User Not Found!!") }
    }
}