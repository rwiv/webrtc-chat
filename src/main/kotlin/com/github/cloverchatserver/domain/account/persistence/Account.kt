package com.github.cloverchatserver.domain.account.persistence

import com.github.cloverchatserver.domain.account.business.data.AccountResponse
import com.github.cloverchatserver.common.error.exception.HttpException
import com.github.cloverchatserver.common.error.exception.NotFoundException
import jakarta.persistence.*

@Entity
@Table(name = "account")
class Account(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    val id: Long?,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val role: AccountRole,

    @Column
    val username: String,

    @Column
    val password: String,

    @Column
    val nickname: String

) {

    fun toResponseUser(): AccountResponse {
        if (id == null) {
            throw NotFoundException("not found account")
        }

        return AccountResponse(id!!, username, nickname)
    }
}