package com.github.cloverchatserver.security.authentication

import com.github.cloverchatserver.domain.user.repository.User
import com.github.cloverchatserver.domain.user.repository.UserRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserPrincipalService(

    val userRepository: UserRepository

) : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserPrincipal {
        if (username.isNullOrEmpty()) {
            throw UsernameNotFoundException("username is null")
        }

        val user: User = userRepository.findByEmail(username)
            ?: throw UsernameNotFoundException("No user found with username: $username")

        val roles = ArrayList<GrantedAuthority>()
        roles.add(SimpleGrantedAuthority("ROLE_${user.role}"))

        return UserPrincipal(user, roles)
    }
}