package com.github.cloverchatserver.domain

import com.github.cloverchatserver.domain.account.business.AccountService
import com.github.cloverchatserver.domain.chatmsg.business.ChatMessageService
import com.github.cloverchatserver.domain.chatmsg.business.data.ChatMessageCreation
import com.github.cloverchatserver.domain.chatmsg.business.UnreadCountCalculator
import com.github.cloverchatserver.domain.chatmsg.persistence.ChatMessage
import com.github.cloverchatserver.domain.chatroom.business.ChatRoomService
import com.github.cloverchatserver.domain.chatuser.business.ChatUserService
import com.github.cloverchatserver.domain.chatuser.business.data.ChatUserCreation
import com.github.cloverchatserver.helpers.ac
import com.github.cloverchatserver.helpers.cr
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
class ChatMessageServiceTest(
    @Autowired private val accountService: AccountService,
    @Autowired private val chatRoomService: ChatRoomService,
    @Autowired private val chatMessageService: ChatMessageService,
    @Autowired private val chatUserService: ChatUserService,
) {

    @Transactional
    @Test fun `test UnreadCalculator`() {
        val a1 = accountService.create(ac("user1"))
        val a2 = accountService.create(ac("user2"))
        val a3 = accountService.create(ac("user3"))
        val a4 = accountService.create(ac("user4"))
        val a5 = accountService.create(ac("user5"))

        val cr = chatRoomService.create(cr(a1.id!!, "cr1"))
        chatUserService.create(ChatUserCreation(cr.id!!, cr.password, a2.id!!))
        chatUserService.create(ChatUserCreation(cr.id!!, cr.password, a3.id!!))
        chatUserService.create(ChatUserCreation(cr.id!!, cr.password, a4.id!!))
        chatUserService.create(ChatUserCreation(cr.id!!, cr.password, a5.id!!))

        val chatMessages = ArrayList<ChatMessage>()
        for (i in 0..9) {
            val chatMessage = chatMessageService.create(ChatMessageCreation(cr.id!!, a1.id!!, "hello$i"))
            chatMessages.add(chatMessage)
        }

        val chatUsers = chatUserService.findByChatRoomId(cr.id!!)
        chatUserService.updateLatestNum(chatUsers[0], 4)
        chatUserService.updateLatestNum(chatUsers[1], 6)
        chatUserService.updateLatestNum(chatUsers[2], 8)
        chatUserService.updateLatestNum(chatUsers[3], 2)

//        chatUsers.forEach { println("${it.account.username}:${it.latestNum}") }
//        chatMessages.forEach { println("${it.content}:${it.num}") }

        val calculator = UnreadCountCalculator()
        val chatUserNums = chatUsers.map { it.latestNum }
        val messageSlice = chatMessages.sortedBy { it.num }.subList(3, 8)
//        val messageSlice = chatMessages.sortedBy { it.num }.subList(5, 10)

        val ret = calculator.calculate(chatUserNums, messageSlice)

        println(ret.reversed().map { "${it.first.num}:${it.second}" })
    }
}
