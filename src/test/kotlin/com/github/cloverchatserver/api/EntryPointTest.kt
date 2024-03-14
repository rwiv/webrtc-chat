package com.github.cloverchatserver.api

import org.junit.jupiter.api.Test
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.RequestEntity

class EntryPointTest {

    private val restTemplate = TestRestTemplate()

    @Test
    fun 세션키_없이_요청() {
        val getRequest = RequestEntity
            .get("http://localhost:11730/test/hello")
            .build()

        val getResponseEntity = restTemplate.exchange(getRequest, String::class.java)
        println(getResponseEntity.body)
    }
}