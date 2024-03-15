package com.github.cloverchatserver.domain.account.api

import com.github.cloverchatserver.domain.account.business.data.AccountResponse
import com.github.cloverchatserver.domain.account.business.data.AccountCreation
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
    fun register(@RequestBody accountCreation: AccountCreation): ResponseEntity<AccountResponse> {
        val user = accountService.create(accountCreation)
        return ResponseEntity.ok().body(AccountResponse.of(user))
    }
}