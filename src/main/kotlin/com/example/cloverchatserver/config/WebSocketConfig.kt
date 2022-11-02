package com.example.cloverchatserver.config

import com.example.cloverchatserver.chat.controller.ChatMessageHandler
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry

@Configuration
@EnableWebSocket
class WebSocketConfig(
    val chatMessageHandler: ChatMessageHandler
) : WebSocketConfigurer {

    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry
            .addHandler(chatMessageHandler, "ws/chat")
            .setAllowedOrigins("*");
    }
}