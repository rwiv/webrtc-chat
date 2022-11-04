package com.example.cloverchatserver.user.service

import com.example.cloverchatserver.user.controller.domain.UserCreateForm
import com.example.cloverchatserver.user.repository.User

interface UserService {

    fun createUser(userCreateForm: UserCreateForm): User

    fun getUserBy(userId: Long): User
}