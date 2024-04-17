package com.github.cloverchatserver.common.env

import com.github.cloverchatserver.domain.account.business.AccountService
import com.github.cloverchatserver.domain.account.business.data.AccountCreation
import com.github.cloverchatserver.domain.account.persistence.Account
import com.github.cloverchatserver.domain.account.persistence.AccountRole
import com.github.cloverchatserver.domain.chatroom.business.ChatRoomService
import com.github.cloverchatserver.domain.chatroom.business.data.ChatRoomCreation
import com.github.cloverchatserver.domain.chatroom.persistence.ChatRoom
import com.github.cloverchatserver.domain.chatroom.persistence.ChatRoomType
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("stage", "prod")
class InitRunnerProd(
    private val accountService: AccountService,
    private val chatRoomService: ChatRoomService,
) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        if (isInit()) {
            return
        }

        val users = ArrayList<Account>()
        users.add(createAdmin())
        users.addAll(createUsers(1, 5))

        val chatRooms = ArrayList<ChatRoom>()
        val user1 = users[1]
        for (i in 1..15) {
            val chatRoomCreation = ChatRoomCreation(user1.id!!, null, "title$i", ChatRoomType.PUBLIC)
            val chatRoom = chatRoomService.create(chatRoomCreation)
            chatRooms.add(chatRoom)
        }
    }

    private fun isInit(): Boolean {
        val user = accountService.findById(1L)

        return user != null
    }

    private fun createAdmin(): Account {
        val creation = AccountCreation(
            AccountRole.ADMIN,
            "admin",
            "admin",
            "admin",
        )
        return accountService.create(creation)
    }

    private fun createUsers(initNum: Int, size: Int): List<Account> {
        val result = ArrayList<Account>()

        val maxNum = initNum + size - 1
        for (i in initNum .. maxNum) {
            val creation = AccountCreation(
                AccountRole.MEMBER,
                "user${i}@gmail.com",
                "1234",
                "user$i",
            )
            val account: Account = accountService.create(creation)

            result.add(account)
        }

        return result
    }
}