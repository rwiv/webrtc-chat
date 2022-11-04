package com.example.cloverchatserver.user.repository

import com.example.cloverchatserver.user.controller.domain.ResponseUser
import com.example.cloverchatserver.user.service.UserNotFoundException
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