package com.example.cloverchatserver.user.controller

import com.example.cloverchatserver.user.repository.Role
import com.example.cloverchatserver.user.repository.User

data class UserCreateForm(

    val email: String,
    val password: String,
    val nickname: String

) {
    fun toUser(): User = User(null, Role.MEMBER, email, password, nickname)
}