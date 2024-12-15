package com.example.chatbot.dto.chat

import com.example.chatbot.entity.chat.ChatThread

data class ChatThreadResponse (
    val threadId: Long,
    val chats: List<ChatResponse>
) {
    companion object {
        fun of (chatThread: ChatThread): ChatThreadResponse {
            return ChatThreadResponse(
                threadId = chatThread.id!!,
                chats = chatThread.chats.map { ChatResponse.of(it) }
            )
        }
    }
}