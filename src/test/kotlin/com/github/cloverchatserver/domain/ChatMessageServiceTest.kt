package com.github.cloverchatserver.domain

import com.github.cloverchatserver.domain.account.business.AccountService
import com.github.cloverchatserver.domain.chatmsg.business.ChatMessageService
import com.github.cloverchatserver.domain.chatmsg.business.data.ChatMessageCreation
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
    @Test fun `test num`() {
        val a1 = accountService.create(ac("user1"))
        val a2 = accountService.create(ac("user2"))
        val a3 = accountService.create(ac("user3"))
        val a4 = accountService.create(ac("user4"))

        val cr = chatRoomService.create(cr(a1.id!!, "cr1"))
        chatUserService.create(ChatUserCreation(cr.id!!, cr.password, a2.id!!))
        chatUserService.create(ChatUserCreation(cr.id!!, cr.password, a3.id!!))
        chatUserService.create(ChatUserCreation(cr.id!!, cr.password, a4.id!!))

        val chatMessages = ArrayList<ChatMessage>()
        for (i in 0..9) {
            val chatMessage = chatMessageService.create(ChatMessageCreation(cr.id!!, a1.id!!, "hello$i"))
            chatMessages.add(chatMessage)
        }

        val chatUsers = chatUserService.findByChatRoomId(cr.id!!)
        chatUserService.updateLatestNum(chatUsers[0], 4)
        chatUserService.updateLatestNum(chatUsers[1], 6)
        chatUserService.updateLatestNum(chatUsers[2], 7)

        chatUsers.forEach { println("${it.account.username}:${it.latestNum}") }
        chatMessages.forEach { println("${it.content}:${it.num}") }

        val chatUserNums = chatUsers.map { it.latestNum }
//        f(chatUserNums, chatMessages)
        println(chatUserNums)
    }

    fun f(chatUserNumsOrigin: List<Int?>, chatMessagesOrigin: List<ChatMessage>) {
        val chatUserNums = chatUserNumsOrigin.filterNotNull().sortedDescending()
        val chatMessages = chatMessagesOrigin.sortedByDescending { it.num }

        val nullSize = chatUserNumsOrigin.size - chatUserNums.size

        val newChatUserNums = ArrayList<Int?>()
        val newChatMessages = ArrayList<ChatMessage?>()
        while (chatUserNums.isNotEmpty() || chatMessages.isNotEmpty()) {
            val chatUserNum = chatUserNums.first()
            val chatMessage = chatMessages.first()

            if (chatUserNum > chatMessage.num) {
                newChatUserNums.add(chatUserNum)
                newChatMessages.add(null)
                chatUserNums.removeFirst()
            } else if (chatUserNum < chatMessage.num) {
                newChatUserNums.add(null)
                newChatMessages.add(chatMessage)
                chatMessages.removeFirst()
            } else {
                newChatUserNums.add(chatUserNum)
                newChatMessages.add(chatMessage)
                chatUserNums.removeFirst()
                chatMessages.removeFirst()
            }
        }

        println(chatUserNums)
        println(chatMessages.map { it.num })
    }
}
