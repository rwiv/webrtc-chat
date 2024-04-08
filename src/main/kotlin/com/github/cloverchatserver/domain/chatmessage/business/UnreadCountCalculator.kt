package com.github.cloverchatserver.domain.chatmessage.business

import com.github.cloverchatserver.domain.chatmessage.persistence.ChatMessage

class UnreadCountCalculator {

    fun calculate(chatUserNumsOrigin: List<Int?>, chatMessagesOrigin: List<ChatMessage>): ArrayList<Pair<ChatMessage, Int>> {
        val tempResult = getTempResult(chatUserNumsOrigin, chatMessagesOrigin)
        val chatUserNums = tempResult.chatUserNums
        val chatMessages = tempResult.chatMessages

        var curCnt = tempResult.chatUserNullSize

        val result = ArrayList<Pair<ChatMessage, Int>>()
        for (i in chatMessages.indices) {
            val chatUserNum = chatUserNums[i]
            val chatMessage = chatMessages[i]

            if (chatUserNum !== null) {
                curCnt--
            }
            if (chatMessage !== null) {
                result.add(Pair(chatMessage, curCnt))
            }
        }
        return result
    }

    private fun getTempResult(chatUserNumsOrigin: List<Int?>, chatMessagesOrigin: List<ChatMessage>): TempResult {
        val chatUserNums = chatUserNumsOrigin.filterNotNull().sortedDescending().toMutableList()
        val chatMessages = chatMessagesOrigin.sortedByDescending { it.num }.toMutableList()

        val newChatUserNums = ArrayList<Int?>()
        val newChatMessages = ArrayList<ChatMessage?>()
        while (chatUserNums.isNotEmpty() || chatMessages.isNotEmpty()) {
            val chatUserNum = chatUserNums.firstOrNull()
            val chatMessage = chatMessages.firstOrNull()

            if (chatUserNum === null) {
                newChatUserNums.add(null)
                newChatMessages.add(chatMessage)
                chatMessages.removeFirst()
                continue
            }
            if (chatMessage === null) {
                newChatUserNums.add(chatUserNum)
                newChatMessages.add(null)
                chatUserNums.removeFirst()
                continue
            }

            if (chatUserNum > chatMessage.num) {
                newChatUserNums.add(chatUserNum)
                newChatMessages.add(null)
                chatUserNums.removeFirst()
            } else if (chatUserNum < chatMessage.num) {
                newChatUserNums.add(null)
                newChatMessages.add(chatMessage)
                chatMessages.removeFirst()
            } else {
                newChatUserNums.add(chatUserNum)
                newChatMessages.add(chatMessage)
                chatUserNums.removeFirst()
                chatMessages.removeFirst()
            }
        }

        assert(newChatUserNums.size == newChatMessages.size)

        val nullSize = chatUserNumsOrigin.size - chatUserNums.size
        return TempResult(newChatUserNums, newChatMessages, nullSize)
    }

    data class TempResult(
        val chatUserNums: List<Int?>,
        val chatMessages: List<ChatMessage?>,
        val chatUserNullSize: Int,
    )
}
