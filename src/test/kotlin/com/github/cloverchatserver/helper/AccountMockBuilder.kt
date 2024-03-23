package com.github.cloverchatserver.helper

import com.github.cloverchatserver.domain.account.business.data.AccountCreation
import com.github.cloverchatserver.domain.account.persistence.AccountRole

fun ac(username: String) = AccountCreation(
    role = AccountRole.MEMBER,
    username = username,
    password = "1234",
    nickname = "${username}!!",
)
