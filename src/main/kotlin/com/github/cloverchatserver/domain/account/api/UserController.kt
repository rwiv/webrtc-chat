package com.github.cloverchatserver.domain.account.api

import com.github.cloverchatserver.domain.account.business.data.AccountResponse
import com.github.cloverchatserver.domain.account.business.data.RequestUserCreateForm
import com.github.cloverchatserver.domain.account.business.AccountService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(
    val accountService: AccountService
) {

    @PostMapping("/register")
    fun register(@RequestBody requestUserCreateForm: RequestUserCreateForm): ResponseEntity<AccountResponse> {
        val user = accountService.create(requestUserCreateForm)

        return ResponseEntity.ok().body(user.toResponseUser())
    }
}