package com.github.cloverchatserver.common.gui

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class GuiController {

    @GetMapping("/")
    fun index(): String {
        return "/dist/index.html"
    }
}