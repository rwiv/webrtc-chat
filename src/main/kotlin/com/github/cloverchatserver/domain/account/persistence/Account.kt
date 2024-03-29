package com.github.cloverchatserver.domain.account.persistence

import jakarta.persistence.*

@Entity
@Table(name = "account", indexes = [
//    Index(name = "idx_username", columnList = "username", unique = true),
    Index(name = "idx_username", columnList = "username"),
])
class Account(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    val id: Long?,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val role: AccountRole,

    @Column(unique = true)
    val username: String,

    @Column
    val password: String,

    @Column
    val nickname: String,
)
