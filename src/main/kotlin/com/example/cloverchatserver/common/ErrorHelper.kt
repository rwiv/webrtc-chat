package com.example.cloverchatserver.common

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import javax.servlet.http.HttpServletResponse

class ErrorHelper {

    companion object {
        fun getResponseEntity(status: HttpStatus, msg: String): ResponseEntity<ResponseError> {
            return ResponseEntity(ResponseError(status.value(), msg), status)
        }

        fun sendError(response: HttpServletResponse, status: HttpStatus, msg: String) {
            val mapper = jacksonObjectMapper()

            response.status = status.value()
            response.contentType = MediaType.APPLICATION_JSON_VALUE

            val responseError = ResponseError(status.value(), msg)

            mapper.writeValue(response.writer, responseError)
        }
    }
}