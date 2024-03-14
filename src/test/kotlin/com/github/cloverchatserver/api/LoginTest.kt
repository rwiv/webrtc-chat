package com.github.cloverchatserver.api

import com.github.cloverchatserver.TestHelper
import com.github.cloverchatserver.domain.user.controller.domain.RequestLoginForm
import com.github.cloverchatserver.domain.user.controller.domain.ResponseUser
import org.junit.jupiter.api.Test
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.MediaType
import org.springframework.http.RequestEntity

class LoginTest {

    private val restTemplate = TestRestTemplate()

    @Test
    fun testLogin() {
        val loginForm = RequestLoginForm("user1@gmail.com", "1234")

        val postRequest = RequestEntity
            .post("http://localhost:11730/user/login")
            .contentType(MediaType.APPLICATION_JSON)
            .body(loginForm)

        val postResponseEntity = restTemplate.exchange(postRequest, ResponseUser::class.java)
        println(postResponseEntity.body)

        val jSessionId = TestHelper().getJSessionId(postResponseEntity.headers)

        val getRequest = RequestEntity
            .get("http://localhost:11730/test/hello")
            .header("Cookie", "JSESSIONID=$jSessionId")
            .build()

        val getResponseEntity = restTemplate.exchange(getRequest, String::class.java)
        println(getResponseEntity.body)
    }
}