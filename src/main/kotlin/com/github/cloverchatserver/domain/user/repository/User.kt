package com.github.cloverchatserver.domain.user.repository

import com.github.cloverchatserver.domain.user.controller.domain.ResponseUser
import com.github.cloverchatserver.domain.user.service.UserNotFoundException
import javax.persistence.*

@Entity
@Table(name = "users")
class User(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    val id: Long?,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val role: Role,

    @Column
    val email: String,

    @Column
    val password: String,

    @Column
    val nickname: String

) {

    fun toResponseUser(): ResponseUser {
        if (id == null) {
            throw UserNotFoundException()
        }

        return ResponseUser(id, email, nickname)
    }
}