package com.example.chatbot.dto.chat

import com.example.chatbot.entity.chat.Chat
import com.example.chatbot.entity.chat.ChatThread
import java.time.OffsetDateTime

data class ChatThreadResponse (
    val threadId: String,
    val chats: MutableList<ChatResponse>,
    val createdAt: OffsetDateTime
) {
    companion object {
        fun of (chatThread: ChatThread): ChatThreadResponse {
            return ChatThreadResponse(
                threadId = chatThread.id,
                chats = chatThread.chats.map { ChatResponse.of(it) }.toMutableList(),
                createdAt = chatThread.createdAt
            )
        }

        fun ofChat(chat: Chat): ChatThreadResponse {
            return ChatThreadResponse(
                threadId = chat.chatThread.id,
                chats = mutableListOf(ChatResponse.of(chat)),
                createdAt = chat.chatThread.createdAt
            )
        }
    }

    fun addChat(chat: Chat) {
        chats.add(ChatResponse.of(chat))
    }
}