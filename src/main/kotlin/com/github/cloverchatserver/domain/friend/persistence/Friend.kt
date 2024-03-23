package com.github.cloverchatserver.domain.friend.persistence

import com.github.cloverchatserver.domain.account.persistence.Account
import jakarta.persistence.*

@Entity
@Table(name = "friend")
class Friend(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friend_id")
    val id: Long?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_id")
    val from: Account,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_id")
    val to: Account,
)
