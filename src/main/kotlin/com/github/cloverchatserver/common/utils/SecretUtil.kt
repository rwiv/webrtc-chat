package com.github.cloverchatserver.common.utils

import java.nio.file.Path
import kotlin.io.path.pathString

object SecretUtil {

    data class ServerSecret(val domainName: String);

    fun getSecret(): ServerSecret {
        val root = Path.of(PathUtil.getRoot().pathString, "secret", "server.json")
        return JsonUtil.readJsonFile<ServerSecret>(root)
    }
}
