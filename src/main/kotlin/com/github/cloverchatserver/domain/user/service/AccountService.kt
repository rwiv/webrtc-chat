package com.github.cloverchatserver.domain.user.service

import com.github.cloverchatserver.domain.user.controller.domain.RequestUserCreateForm
import com.github.cloverchatserver.domain.user.repository.Account

interface AccountService {

    fun createUser(requestUserCreateForm: RequestUserCreateForm): Account

    fun getUserBy(userId: Long): Account?
    fun findByEmail(email: String): Account?
}