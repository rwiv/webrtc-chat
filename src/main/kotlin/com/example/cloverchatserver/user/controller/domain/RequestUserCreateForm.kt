package com.example.cloverchatserver.user.controller.domain

import com.example.cloverchatserver.user.repository.Role
import com.example.cloverchatserver.user.repository.User
import org.springframework.security.crypto.password.PasswordEncoder

data class RequestUserCreateForm(

    val email: String,
    val password: String,
    val nickname: String

) {
    fun toUser(passwordEncoder: PasswordEncoder): User = User(
        null, Role.MEMBER, email, passwordEncoder.encode(password), nickname
    )
}