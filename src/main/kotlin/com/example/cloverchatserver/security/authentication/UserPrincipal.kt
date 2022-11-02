package com.example.cloverchatserver.security.authentication

import com.example.cloverchatserver.user.repository.User
import org.springframework.security.core.GrantedAuthority

data class UserPrincipal(

    val user: User,

    private val roles: List<GrantedAuthority>

) : org.springframework.security.core.userdetails.User(
    user.email,
    user.password,
    true, true, true, true,
    roles
)