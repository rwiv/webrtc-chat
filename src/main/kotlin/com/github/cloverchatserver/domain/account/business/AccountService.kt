package com.github.cloverchatserver.domain.account.business

import com.github.cloverchatserver.domain.account.api.domain.RequestUserCreateForm
import com.github.cloverchatserver.domain.account.persistence.Account

interface AccountService {

    fun createUser(requestUserCreateForm: RequestUserCreateForm): Account

    fun getUserBy(userId: Long): Account?
    fun findByUsername(username: String): Account?
}