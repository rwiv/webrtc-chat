package com.github.cloverchatserver

import org.springframework.http.HttpHeaders

class TestHelper {

    fun getJSessionId(headers: HttpHeaders): String {
        val cookie = headers["Set-Cookie"]?.get(0)
        val cookieEntries = cookie?.split(";")

        var jSessionId = ""
        cookieEntries?.forEach { cookieEntry ->
            val rawStr = cookieEntry.trim()

            if (rawStr.startsWith("JSESSIONID")) {
                jSessionId = rawStr.split("=")[1]
            }
        }

        return jSessionId
    }
}