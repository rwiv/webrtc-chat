package com.example.cloverchatserver.domain.user.service

import com.example.cloverchatserver.domain.user.controller.domain.RequestUserCreateForm
import com.example.cloverchatserver.domain.user.repository.User

interface UserService {

    fun createUser(requestUserCreateForm: RequestUserCreateForm): User

    fun getUserBy(userId: Long): User?
}