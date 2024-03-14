package com.github.cloverchatserver.dev

import com.github.cloverchatserver.domain.chatroom.api.domain.RequestChatRoomCreateForm
import com.github.cloverchatserver.domain.chatroom.persistence.ChatRoomType
import com.github.cloverchatserver.domain.chatroom.business.ChatRoomService
import com.github.cloverchatserver.domain.account.api.domain.RequestUserCreateForm
import com.github.cloverchatserver.domain.account.persistence.Account
import com.github.cloverchatserver.domain.account.business.AccountService
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("dev")
class DevInitRunner(

    val chatRoomService: ChatRoomService,
    val accountService: AccountService

) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        if (isProd()) {
            return
        }

        val users = createUsers(1, 5)
        for (i in 1..5) {
            val requestChatRoomCreateForm = RequestChatRoomCreateForm(users[0].id!!, null, "title$i", ChatRoomType.PUBLIC)
            chatRoomService.createChatRoom(requestChatRoomCreateForm)
        }
    }

    private fun isProd(): Boolean {
        val user = accountService.getUserBy(1L)

        return user != null
    }

    private fun createUsers(initNum: Int, size: Int): List<Account> {
        val result = ArrayList<Account>()

        val maxNum = initNum + size - 1
        for (i in initNum .. maxNum) {
            val form = RequestUserCreateForm("user${i}@gmail.com", "1234", "user$i")
            val account: Account = accountService.createUser(form)

            result.add(account)
        }

        return result
    }
}