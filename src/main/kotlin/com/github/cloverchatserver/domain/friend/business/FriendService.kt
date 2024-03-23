package com.github.cloverchatserver.domain.friend.business

import com.github.cloverchatserver.common.error.exception.NotFoundException
import com.github.cloverchatserver.domain.account.persistence.Account
import com.github.cloverchatserver.domain.account.persistence.AccountRepository
import com.github.cloverchatserver.domain.friend.persistence.Friend
import com.github.cloverchatserver.domain.friend.persistence.FriendRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull

@Service
class FriendService(
    private val friendRepository: FriendRepository,
    private val accountRepository: AccountRepository,
) {

    @Transactional
    fun add(from: Account, to: Account): Friend {
        val tbc = Friend(null, from, to)
        return friendRepository.save(tbc)
    }

    @Transactional
    fun add(fromAccountId: Long, toAccountId: Long): Friend {
        val from = accountRepository.findById(fromAccountId).getOrNull()
            ?: throw NotFoundException("not found from")
        val to = accountRepository.findById(toAccountId).getOrNull()
            ?: throw NotFoundException("not found to")

        return add(from, to)
    }

    fun findByAccount(from: Account): List<Friend> {
        return friendRepository.findByFrom(from)
    }
}