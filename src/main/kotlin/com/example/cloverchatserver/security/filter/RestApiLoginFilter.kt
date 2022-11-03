package com.example.cloverchatserver.security.filter

import com.example.cloverchatserver.security.authentication.AuthenticationToken
import com.example.cloverchatserver.user.controller.RequestLoginForm
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class RestApiLoginFilter: AbstractAuthenticationProcessingFilter(
    AntPathRequestMatcher("/user/login", "POST")
) {

    private val objectMapper: ObjectMapper = jacksonObjectMapper()

    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
        if (!request!!.method.equals("POST")) {
            throw IllegalArgumentException("Authentication method not supported");
        }

        val loginForm: RequestLoginForm = objectMapper.readValue(request.reader, RequestLoginForm::class.java)

        if (loginForm.email.isEmpty() || loginForm.password.isEmpty()) {
            throw AuthenticationServiceException("Username or Password not provided")
        }

        val requestToken = AuthenticationToken(loginForm.email, loginForm.password)
        val successToken = authenticationManager.authenticate(requestToken)

        return successToken
    }
}