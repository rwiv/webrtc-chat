package com.github.cloverchatserver.domain.user.controller

import com.github.cloverchatserver.domain.user.controller.domain.ResponseUser
import com.github.cloverchatserver.domain.user.controller.domain.RequestUserCreateForm
import com.github.cloverchatserver.domain.user.service.UserService
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
    fun register(@RequestBody requestUserCreateForm: RequestUserCreateForm): ResponseEntity<ResponseUser> {
        val user = userService.createUser(requestUserCreateForm)

        return ResponseEntity.ok().body(user.toResponseUser())
    }
}