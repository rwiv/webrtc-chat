package com.github.cloverchatserver.error.exception

open class MustCatchException(
    message: String? = null,
    cause: Throwable? = null,
) : RuntimeException(message, cause)
