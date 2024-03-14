package com.github.cloverchatserver.common.dev

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/dev")
class DevController {

    @GetMapping("/hello")
    fun hello(): String {
        return "hello world!"
    }
}