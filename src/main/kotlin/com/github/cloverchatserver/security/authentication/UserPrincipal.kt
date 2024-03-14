package com.github.cloverchatserver.security.authentication

import com.github.cloverchatserver.domain.user.repository.User
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