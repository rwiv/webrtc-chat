package com.example.cloverchatserver.config

import com.example.cloverchatserver.board.repository.ChatRoom
import com.example.cloverchatserver.board.repository.ChatRoomRepository
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class TestInjector(
    val chatRoomRepository: ChatRoomRepository
) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        for (i in 0..5) {
            chatRoomRepository.save(ChatRoom(null, "user$i", "1234", "title$i", LocalDateTime.now()))
        }
    }

}