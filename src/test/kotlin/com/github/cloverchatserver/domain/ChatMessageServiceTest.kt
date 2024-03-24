package com.github.cloverchatserver.domain

import com.github.cloverchatserver.domain.account.business.AccountService
import com.github.cloverchatserver.domain.chatmsg.business.ChatMessageService
import com.github.cloverchatserver.domain.chatmsg.business.data.ChatMessageCreation
import com.github.cloverchatserver.domain.chatmsg.persistence.ChatMessageRepository
import com.github.cloverchatserver.domain.chatroom.business.ChatRoomService
import com.github.cloverchatserver.helpers.ac
import com.github.cloverchatserver.helpers.cr
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ChatMessageServiceTest(
    @Autowired private val accountService: AccountService,
    @Autowired private val chatRoomService: ChatRoomService,
    @Autowired private val chatMessageService: ChatMessageService,
    @Autowired private val chatMessageRepository: ChatMessageRepository,
) {

    @Test fun `test count query`() {
        val a1 = accountService.create(ac("user1"))

        val cr1 = chatRoomService.create(cr(a1.id!!, "cr1"), a1.id!!)
        val cr2 = chatRoomService.create(cr(a1.id!!, "cr2"), a1.id!!)

        for (i in 1..10) {
            chatMessageService.create(ChatMessageCreation(cr1.id!!, a1.id!!, "hello$i"))
        }

        for (i in 1..10) {
            chatMessageService.create(ChatMessageCreation(cr2.id!!, a1.id!!, "hello$i"))
        }

        val ret = chatMessageRepository.findOneByChatRoom(cr1)
        println(ret?.content)
    }
}