package com.example.chatbot.dto.chat

import com.example.chatbot.entity.chat.Chat
import java.time.OffsetDateTime

data class ChatResponse (
    val chatId: String,
    val question: String,
    val answer: String,
    val createdAt: OffsetDateTime
) {
    companion object {
        fun of (chat: Chat): ChatResponse {
            return ChatResponse(
                chatId = chat.id,
                question = chat.question,
                answer = chat.answer,
                createdAt = chat.createdAt
            )
        }
    }
}