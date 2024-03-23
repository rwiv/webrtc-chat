package com.github.cloverchatserver.domain

import com.github.cloverchatserver.domain.account.business.AccountService
import com.github.cloverchatserver.domain.chatroom.business.ChatRoomService
import com.github.cloverchatserver.domain.chatuser.business.ChatUserService
import com.github.cloverchatserver.helper.ac
import com.github.cloverchatserver.helper.cr
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ChatRoomServiceTest(
    @Autowired private val accountService: AccountService,
    @Autowired private val chatRoomService: ChatRoomService,
    @Autowired private val chatUserService: ChatUserService,
) {

    @Test fun `test find by account`() {
        val a1 = accountService.create(ac("user1"))

        val cr1 = chatRoomService.create(cr(a1.id!!, "cr1"), a1.id!!)
        val cu1 = chatUserService.create(cr1.id!!, null, a1.id!!)

        val cr2 = chatRoomService.create(cr(a1.id!!, "cr2"), a1.id!!)
        val cu2 = chatUserService.create(cr2.id!!, null, a1.id!!)

        val chatRooms = chatRoomService.findByAccount(a1)
        chatRooms.forEach { println(it.title) }
    }
}
