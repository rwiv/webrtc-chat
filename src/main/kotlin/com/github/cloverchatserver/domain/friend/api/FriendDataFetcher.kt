package com.github.cloverchatserver.domain.friend.api

import com.github.cloverchatserver.domain.friend.business.FriendService
import com.github.cloverchatserver.domain.friend.persistence.Friend
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.InputArgument

@DgsComponent
class FriendDataFetcher(
    private val friendService: FriendService,
) {

    @DgsMutation
    fun addFriend(
        @InputArgument fromAccountId: Long,
        @InputArgument toAccountId: Long,
    ): Friend {
        return friendService.add(fromAccountId, toAccountId)
    }
}
