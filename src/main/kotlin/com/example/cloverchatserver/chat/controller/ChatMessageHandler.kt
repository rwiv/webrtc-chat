package com.example.cloverchatserver.chat.controller

import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

@Component
class ChatMessageHandler : TextWebSocketHandler() {

    private val store: MutableMap<String, WebSocketSession> = HashMap()

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        session.sendMessage(message)
    }

    override fun afterConnectionEstablished(session: WebSocketSession) {
        println("connected")
        store[session.id] = session
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        println("closed")
        store.remove(session.id)
    }

    override fun handleTransportError(session: WebSocketSession, exception: Throwable) {
//        println("err")
//        println(exception.message)
//        exception.printStackTrace()
    }
}