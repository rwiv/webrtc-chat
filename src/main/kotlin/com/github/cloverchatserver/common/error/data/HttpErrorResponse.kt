package com.projectd.bootapi.error.data

import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonPropertyOrder("status", "message", "uuid", "timestamp", "exception")
data class HttpErrorResponse(
    val status: Int,
    val message: String,
    val uuid: String,
    val timestamp: String,
    val exception: String,
)
