package com.example.cloverchatserver.user.controller

import com.example.cloverchatserver.user.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(
    val userService: UserService
) {

    @PostMapping("/register")
    fun register(@RequestBody userCreateForm: UserCreateForm): ResponseEntity<ResponseUser> {
        val user = userService.createUser(userCreateForm)

        return ResponseEntity.ok().body(user.toResponseUser())
    }
}