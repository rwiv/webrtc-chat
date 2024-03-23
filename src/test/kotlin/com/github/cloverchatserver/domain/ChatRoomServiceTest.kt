package com.github.cloverchatserver.domain

import com.github.cloverchatserver.domain.account.business.AccountService
import com.github.cloverchatserver.domain.chatroom.business.ChatRoomService
import com.github.cloverchatserver.helpers.ac
import com.github.cloverchatserver.helpers.cr
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
class ChatRoomServiceTest(
    @Autowired private val accountService: AccountService,
    @Autowired private val chatRoomService: ChatRoomService,
) {

    @Transactional
    @Test fun `test find by account`() {
        val a1 = accountService.create(ac("user1"))

        val cr1 = chatRoomService.create(cr(a1.id!!, "cr1"), a1.id!!)
        val cr2 = chatRoomService.create(cr(a1.id!!, "cr2"), a1.id!!)

        val chatRooms = chatRoomService.findByAccount(a1)
        chatRooms.forEach { println(it.title) }
    }
}
