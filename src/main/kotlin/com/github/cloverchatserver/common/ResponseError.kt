package com.github.cloverchatserver.common

import java.io.Serializable
import java.time.LocalDateTime

data class ResponseError(
    val status: Int,
    val code: String
): Serializable {

    val timestamp: String = LocalDateTime.now().toString()
}