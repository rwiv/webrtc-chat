package com.github.cloverchatserver.domain

import com.github.cloverchatserver.domain.account.business.AccountService
import com.github.cloverchatserver.domain.friend.business.FriendService
import com.github.cloverchatserver.helpers.ac
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
class FriendServiceTest(
    @Autowired private val accountService: AccountService,
    @Autowired private val friendService: FriendService,
) {

    @Transactional
    @Test fun `test friend creation`() {
        val a1 = accountService.create(ac("user1"))
        val a2 = accountService.create(ac("user2"))
        val a3 = accountService.create(ac("user3"))

        friendService.add(a1, a2)
        friendService.add(a1, a3)

        val friends = friendService.findByAccount(a1).map { it.to }

        friends.forEach { println(it.username) }
    }
}
