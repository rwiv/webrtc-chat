package com.github.cloverchatserver.common.dev

import org.springframework.context.annotation.Profile
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Profile("dev")
@RestController
@RequestMapping("/dev")
class DevController {

    @GetMapping("/hello")
    fun hello(): String {
        return "hello"
    }
}
