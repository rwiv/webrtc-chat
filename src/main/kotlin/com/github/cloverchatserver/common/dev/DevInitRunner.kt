package com.github.cloverchatserver.common.dev

import com.github.cloverchatserver.domain.account.business.AccountService
import com.github.cloverchatserver.domain.account.business.data.AccountCreation
import com.github.cloverchatserver.domain.account.persistence.Account
import com.github.cloverchatserver.domain.account.persistence.AccountRole
import com.github.cloverchatserver.domain.chatmsg.business.ChatMessageService
import com.github.cloverchatserver.domain.chatmsg.business.data.ChatMessageCreation
import com.github.cloverchatserver.domain.chatroom.business.ChatRoomService
import com.github.cloverchatserver.domain.chatroom.business.data.ChatRoomCreation
import com.github.cloverchatserver.domain.chatroom.persistence.ChatRoom
import com.github.cloverchatserver.domain.chatroom.persistence.ChatRoomType
import com.github.cloverchatserver.domain.chatuser.business.ChatUserService
import com.github.cloverchatserver.domain.friend.business.FriendService
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("dev")
class DevInitRunner(
    private val accountService: AccountService,
    private val friendService: FriendService,
    private val chatRoomService: ChatRoomService,
    private val chatUserService: ChatUserService,
    private val chatMessageService: ChatMessageService,
) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        if (isProd()) {
            return
        }

        val users = ArrayList<Account>()
        users.add(createAdmin())
        users.addAll(createUsers(1, 5))

        val chatRooms = ArrayList<ChatRoom>()
        val user0 = users[0]
        for (i in 1..5) {
            val chatRoomCreation = ChatRoomCreation(users[i].id!!, null, "title$i", ChatRoomType.PUBLIC)
            val chatRoom = chatRoomService.create(chatRoomCreation, users[i].id!!)
            chatRooms.add(chatRoom)
        }

        chatRooms.forEach { chatRoom ->
            chatUserService.create(chatRoom.id!!, chatRoom.password, user0.id!!)
        }

        val chatRoom = chatRooms[0]
        for (i in 1..5) {
            chatMessageService.create(ChatMessageCreation(chatRoom.id!!, user0.id!!, "hello$i"))
        }

        val user2 = users[2]
        friendService.add(user2, users[3])
        friendService.add(user2, users[4])
    }

    private fun isProd(): Boolean {
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