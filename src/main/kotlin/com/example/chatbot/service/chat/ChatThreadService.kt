package com.example.chatbot.service.chat

import com.example.chatbot.entity.chat.ChatThread
import com.example.chatbot.repository.ChatThreadRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime

@Service
@Transactional
class ChatThreadService (
    private val chatThreadRepository: ChatThreadRepository
) {

    @Transactional
    fun getOrCreateThread(userId: String): ChatThread {
        val recentThread = chatThreadRepository.findByUserIdOrderByUpdatedAtDesc(userId).firstOrNull()

        if (recentThread == null || recentThread.updatedAt.isBefore(OffsetDateTime.now().minusMinutes(30))) {
            val newThread = ChatThread(userId)
            return chatThreadRepository.save(newThread)
        }

        return recentThread
    }

    @Transactional
    fun updateThreadTimestamp(chatThread: ChatThread) {
        chatThread.updatedAt = OffsetDateTime.now()
        chatThreadRepository.save(chatThread)
    }
}