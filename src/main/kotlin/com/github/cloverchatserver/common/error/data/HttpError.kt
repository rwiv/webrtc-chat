package com.github.cloverchatserver.common.error.data

import com.github.cloverchatserver.common.error.exception.HttpException
import com.projectd.bootapi.error.data.HttpErrorResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import java.io.ByteArrayOutputStream
import java.nio.charset.StandardCharsets
import java.time.LocalDateTime

class HttpError(
    val uuid: String,
    val timestamp: LocalDateTime,
    val exception: HttpException,
    req: HttpServletRequest,
) {
    val status: Int = exception.status
    val message: String = exception.userMessage ?: exception.message ?: ""
    val headers: Map<String, String>? = exception.headers

    val path: String = req.requestURI
    val params: Map<String, Array<String>> = req.parameterMap
    val body: String? = getBody(req)

    val errors: Any? = exception.errors
    val stackTrace: String = exception.stackTraceToString()

    fun toResponse() = HttpErrorResponse(
        status = status,
        message = message,
        uuid = uuid,
        timestamp = timestamp.toString(),
        exception = exception.javaClass.simpleName
    )

    fun toResponseEntity(): ResponseEntity<HttpErrorResponse> {
        return ResponseEntity
            .status(status)
            .headers { headers?.forEach { header -> it[header.key] = header.value } }
            .body(toResponse())
    }

    private fun getBody(req: HttpServletRequest): String? {
        val limitCnt = 100 // 100KB

        val rs = req.inputStream
        val ws = ByteArrayOutputStream()
        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)

        var cnt = 0
        var nRead: Int
        while (rs.read(buffer, 0, buffer.size).also { nRead = it } != -1) {
            if (cnt > limitCnt) {
                return null
            }
            ws.write(buffer, 0, nRead)
            cnt++
        }
        ws.flush()

        val byteArray: ByteArray = ws.toByteArray()
        return String(byteArray, StandardCharsets.UTF_8)
    }
}