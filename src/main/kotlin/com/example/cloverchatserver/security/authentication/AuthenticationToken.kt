package com.example.cloverchatserver.security.authentication

import com.example.cloverchatserver.user.controller.ResponseUser
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class AuthenticationToken(

    private val principal: String,
    private val credentials: String?,

    authorities: Collection<out GrantedAuthority>? = null,
    responseUser: ResponseUser? = null

) : AbstractAuthenticationToken(authorities) {

    init {
        super.setAuthenticated(false)
        super.setDetails(responseUser)
    }

    constructor(
        principal: String, authorities: Collection<out GrantedAuthority>?, responseUser: ResponseUser?
    ) : this(principal, null, authorities, responseUser) {
        super.setAuthenticated(true)
        super.setDetails(responseUser)
    }

    override fun getPrincipal(): String = principal
    override fun getCredentials(): String? = credentials
}