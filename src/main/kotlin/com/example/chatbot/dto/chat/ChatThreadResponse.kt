package com.example.chatbot.dto.chat

import com.example.chatbot.entity.chat.Chat
import com.example.chatbot.entity.chat.ChatThread
import java.time.OffsetDateTime

data class ChatThreadResponse (
    val userId: String,
    val threadId: String,
    val chats: List<ChatResponse>,
    val createdAt: OffsetDateTime
) {
    companion object {
        fun of (chatThread: ChatThread): ChatThreadResponse {
            return ChatThreadResponse(
                userId = chatThread.userId,
                threadId = chatThread.id,
                chats = chatThread.chats.map { ChatResponse.of(it) },
                createdAt = chatThread.createdAt
            )
        }

        fun fromChats(threadId: String, chats: List<Chat>): ChatThreadResponse {
            val userId = chats.first().chatThread.userId
            val chatResponses = chats.map { ChatResponse.of(it) }
            val createdAt = chats.first().chatThread.createdAt
            return ChatThreadResponse(userId, threadId, chatResponses, createdAt)
        }
    }
}