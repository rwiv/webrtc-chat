package com.github.cloverchatserver.domain.account.api

import com.github.cloverchatserver.domain.account.business.data.AccountResponse
import com.github.cloverchatserver.domain.account.business.data.AccountCreation
import com.github.cloverchatserver.domain.account.business.AccountService
import com.github.cloverchatserver.domain.account.persistence.Account
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(
    val accountService: AccountService
) {

    @GetMapping("/me")
    fun me(authentication: Authentication): Account? {
        if (authentication is AnonymousAuthenticationToken) {
            return null
        }
        val accountResponse = authentication.details as AccountResponse
        return accountService.findById(accountResponse.id)
    }

    @PostMapping("/register")
    fun register(@RequestBody accountCreation: AccountCreation): ResponseEntity<AccountResponse> {
        val user = accountService.create(accountCreation)
        return ResponseEntity.ok().body(AccountResponse.of(user))
    }
}
