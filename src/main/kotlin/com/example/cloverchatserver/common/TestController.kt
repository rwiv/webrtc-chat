package com.example.cloverchatserver.common

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping("/test")
class TestController {

    @GetMapping("/hello")
    @ResponseBody
    fun healthCheck(): String {
        return "hello"
    }

    @GetMapping("/admin")
    @ResponseBody
    fun adminTest(): String {
        return "admin"
    }


    @GetMapping("/stomp")
    fun stompTest(): String {
        return "stomp/test"
    }
}