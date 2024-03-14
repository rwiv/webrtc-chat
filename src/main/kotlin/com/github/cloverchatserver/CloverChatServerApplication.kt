package com.github.cloverchatserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CloverChatServerApplication

fun main(args: Array<String>) {
	runApplication<CloverChatServerApplication>(*args)
}
