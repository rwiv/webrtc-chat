package com.example.cloverchatserver.api

import com.example.cloverchatserver.user.controller.RequestLoginForm
import com.example.cloverchatserver.user.controller.ResponseUser
import org.junit.jupiter.api.Test
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpHeaders
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

        val jSessionId = getJSessionId(postResponseEntity.headers)

        val getRequest = RequestEntity
            .get("http://localhost:11730/test/hello")
            .header("Cookie", "JSESSIONID=$jSessionId")
            .build()

        val getResponseEntity = restTemplate.exchange(getRequest, String::class.java)
        println(getResponseEntity.body)
    }

    private fun getJSessionId(headers: HttpHeaders): String {
        val cookie = headers["Set-Cookie"]?.get(0)
        val cookieEntries = cookie?.split(";")

        var jSessionId = ""
        cookieEntries?.forEach { cookieEntry ->
            val rawStr = cookieEntry.trim()

            if (rawStr.startsWith("JSESSIONID")) {
                jSessionId = rawStr.split("=")[1]
            }
        }

        return jSessionId
    }
}