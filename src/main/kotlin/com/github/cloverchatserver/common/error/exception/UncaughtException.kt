package com.github.cloverchatserver.common.error.exception

class UncaughtException(
    status: Int,
    cause: MustCatchException,
    message: String? = "uncaught ${cause.javaClass.simpleName}",
) : HttpException(status, message, cause)
