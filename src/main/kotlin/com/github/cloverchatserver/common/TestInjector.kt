package com.github.cloverchatserver.common

import com.github.cloverchatserver.domain.chatroom.controller.domain.RequestChatRoomCreateForm
import com.github.cloverchatserver.domain.chatroom.repository.ChatRoomType
import com.github.cloverchatserver.domain.chatroom.service.ChatRoomService
import com.github.cloverchatserver.domain.user.controller.domain.RequestUserCreateForm
import com.github.cloverchatserver.domain.user.repository.User
import com.github.cloverchatserver.domain.user.service.UserService
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class TestInjector(

    val chatRoomService: ChatRoomService,
    val userService: UserService

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
        val user = userService.getUserBy(1L)

        return user != null
    }

    private fun createUsers(initNum: Int, size: Int): List<User> {
        val result = ArrayList<User>()

        val maxNum = initNum + size - 1
        for (i in initNum .. maxNum) {
            val form = RequestUserCreateForm("user${i}@gmail.com", "1234", "user$i")
            val user: User = userService.createUser(form)

            result.add(user)
        }

        return result
    }
}