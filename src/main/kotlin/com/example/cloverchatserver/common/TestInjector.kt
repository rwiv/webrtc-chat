package com.example.cloverchatserver.common

import com.example.cloverchatserver.board.controller.ChatRoomCreateForm
import com.example.cloverchatserver.board.repository.ChatRoom
import com.example.cloverchatserver.board.repository.ChatRoomRepository
import com.example.cloverchatserver.board.repository.ChatRoomType
import com.example.cloverchatserver.board.service.ChatRoomService
import com.example.cloverchatserver.user.controller.UserCreateForm
import com.example.cloverchatserver.user.repository.User
import com.example.cloverchatserver.user.repository.UserRepository
import com.example.cloverchatserver.user.service.UserService
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class TestInjector(

    val chatRoomService: ChatRoomService,
    val userService: UserService

) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        val users = createUsers(1, 5)
        for (i in 0..5) {
            val chatRoomCreateForm = ChatRoomCreateForm(users[0].id!!, "1234", "title${i+1}", ChatRoomType.PUBLIC)
            chatRoomService.createChatRoom(chatRoomCreateForm)
        }
    }

    private fun createUsers(initNum: Int, size: Int): List<User> {
        val result = ArrayList<User>()

        val maxNum = initNum + size - 1
        for (i in initNum .. maxNum) {
            val form = UserCreateForm("user${i}@gmail.com", "1234", "user$i")
            val user: User = userService.createUser(form)

            result.add(user)
        }

        return result
    }

}