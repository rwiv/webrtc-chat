package com.example.cloverchatserver.common

import com.example.cloverchatserver.board.controller.domain.RequestChatRoomCreateForm
import com.example.cloverchatserver.board.repository.ChatRoomType
import com.example.cloverchatserver.board.service.ChatRoomService
import com.example.cloverchatserver.user.controller.domain.RequestUserCreateForm
import com.example.cloverchatserver.user.repository.User
import com.example.cloverchatserver.user.service.UserService
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class TestInjector(

    val chatRoomService: ChatRoomService,
    val userService: UserService

) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        val users = createUsers(1, 5)
        for (i in 0..5) {
            val requestChatRoomCreateForm = RequestChatRoomCreateForm(users[0].id!!, "1234", "title${i+1}", ChatRoomType.PUBLIC)
            chatRoomService.createChatRoom(requestChatRoomCreateForm)
        }
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