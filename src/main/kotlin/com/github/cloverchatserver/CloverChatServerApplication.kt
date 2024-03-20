package com.github.cloverchatserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@EnableAsync
class CloverChatServerApplication

fun main(args: Array<String>) {
	runApplication<CloverChatServerApplication>(*args)
}
