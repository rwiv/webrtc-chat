package com.github.cloverchatserver.domain

import com.github.cloverchatserver.domain.account.business.AccountService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
class AccountServiceTest(
    @Autowired private val accountService: AccountService,
) {

    @Transactional
    @Test fun test() {
        val page = 1
        val size = 3
        val res = accountService.findByPage(page, size)
        res.forEach { println(it.username) }
    }
}
