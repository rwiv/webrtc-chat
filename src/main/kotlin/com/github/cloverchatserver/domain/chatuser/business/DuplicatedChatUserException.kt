package com.github.cloverchatserver.domain.chatuser.business

import com.github.cloverchatserver.common.error.exception.HttpException

class DuplicatedChatUserException(message: String) : HttpException(422, message)