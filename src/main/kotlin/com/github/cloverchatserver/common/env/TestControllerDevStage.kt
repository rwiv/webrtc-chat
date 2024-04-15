package com.github.cloverchatserver.common.env

import org.springframework.context.annotation.Profile
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Profile("dev", "stage")
@RestController
@RequestMapping("/dev")
class TestControllerDevStage {

    @GetMapping("/hello")
    fun hello(): String {
        return "hello"
    }
}
