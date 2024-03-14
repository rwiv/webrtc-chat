package com.github.cloverchatserver.domain.account.persistence

import com.github.cloverchatserver.domain.account.api.domain.ResponseUser
import com.github.cloverchatserver.domain.account.business.AccountNotFoundException
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

    fun toResponseUser(): ResponseUser {
        if (id == null) {
            throw AccountNotFoundException()
        }

        return ResponseUser(id!!, username, nickname)
    }
}