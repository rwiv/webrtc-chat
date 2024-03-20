package com.github.cloverchatserver.domain.chatmsg.business

import com.github.cloverchatserver.domain.chatmsg.business.data.ChatMessageReadEvent
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class ChatMessageEventListener {

    @Async
    @EventListener
    fun handleReadEvent(event: ChatMessageReadEvent) {
        Thread.sleep(1000)
        println(event.value)
    }
}
