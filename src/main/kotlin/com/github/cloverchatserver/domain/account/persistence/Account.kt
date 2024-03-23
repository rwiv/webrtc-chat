package com.github.cloverchatserver.domain.account.persistence

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
    val nickname: String,
)
