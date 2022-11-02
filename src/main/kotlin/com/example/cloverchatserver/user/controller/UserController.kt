package com.example.cloverchatserver.user.controller

import com.example.cloverchatserver.user.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    val userService: UserService
) {

    @PostMapping("/register")
    fun register(userCreateForm: UserCreateForm): ResponseEntity<ResponseUser> {
        val user = userService.createUser(userCreateForm)

        return ResponseEntity.ok().body(user.toResponseUser())
    }
}