package com.example.cloverchatserver.user.service

import com.example.cloverchatserver.user.controller.domain.RequestUserCreateForm
import com.example.cloverchatserver.user.repository.User

interface UserService {

    fun createUser(requestUserCreateForm: RequestUserCreateForm): User

    fun getUserBy(userId: Long): User
}