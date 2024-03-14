package com.github.cloverchatserver.domain.user.service

import com.github.cloverchatserver.domain.user.controller.domain.RequestUserCreateForm
import com.github.cloverchatserver.domain.user.repository.User

interface UserService {

    fun createUser(requestUserCreateForm: RequestUserCreateForm): User

    fun getUserBy(userId: Long): User?
}