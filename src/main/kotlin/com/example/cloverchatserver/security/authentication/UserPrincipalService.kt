package com.example.cloverchatserver.security.authentication

import com.example.cloverchatserver.user.repository.User
import com.example.cloverchatserver.user.service.UserNotFoundException
import com.example.cloverchatserver.user.repository.UserRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserPrincipalService(

    val userRepository: UserRepository

) : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserPrincipal {
        if (username.isNullOrEmpty()) {
            throw UserNotFoundException()
        }

        val user: User = userRepository.findByEmail(username)
            ?: throw UserNotFoundException("No user found with username: $username")

        val roles = ArrayList<GrantedAuthority>()
        roles.add(SimpleGrantedAuthority("ROLE_${user.role}"))

        return UserPrincipal(user, roles)
    }
}