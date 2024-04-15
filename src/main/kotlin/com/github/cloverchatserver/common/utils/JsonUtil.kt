package com.github.cloverchatserver.common.utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path

object JsonUtil {

    val mapper: ObjectMapper = jacksonObjectMapper()

    inline fun <reified T> readJsonFile(path: Path): T {
        val jsonString = Files.readString(path)
        return readJson(jsonString)
    }

    fun <T> r(jsonString: String): T {
        return mapper.readValue(jsonString, Any::class.java) as T
    }

    inline fun <reified T> readJson(jsonString: InputStream): T {
        return mapper.readValue(jsonString, T::class.java)
    }

    inline fun <reified T> readJson(jsonString: String): T {
        return mapper.readValue(jsonString, T::class.java)
    }

    fun <T> toJsonBytes(obj: T): ByteArray {
        return mapper.writeValueAsBytes(obj)
    }

    fun <T> toJsonString(obj: T): String {
        return mapper.writeValueAsString(obj)
    }

    fun <T> writeFile(obj:T, dest: Path) {
        mapper
            .writerWithDefaultPrettyPrinter()
            .writeValue(dest.toFile(), obj)
    }
}
