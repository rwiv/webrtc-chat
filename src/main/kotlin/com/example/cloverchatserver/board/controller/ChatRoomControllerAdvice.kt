package com.example.cloverchatserver.board.controller

import com.example.cloverchatserver.board.service.ChatRoomNotFoundException
import com.example.cloverchatserver.common.ResponseError
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice(assignableTypes = [ ChatRoomController::class ])
class ChatRoomControllerAdvice {

    @ExceptionHandler(value = [ BadCredentialsException::class ])
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleBadCredentialsException(e: BadCredentialsException): ResponseError {
        return ResponseError(HttpStatus.UNAUTHORIZED.value(), "BadCredentialsException")
    }

    @ExceptionHandler(value = [ ChatRoomNotFoundException::class ])
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleChatRoomNotFoundException(e: ChatRoomNotFoundException): ResponseError {
        return ResponseError(HttpStatus.NOT_FOUND.value(), "ChatRoomNotFoundException")
    }
}