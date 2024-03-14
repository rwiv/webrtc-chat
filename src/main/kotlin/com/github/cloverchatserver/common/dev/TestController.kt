package com.github.cloverchatserver.common.dev

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/test")
class TestController {

    @GetMapping("/stomp")
    fun stompTest(): String {
        return "stomp/test"
    }
}