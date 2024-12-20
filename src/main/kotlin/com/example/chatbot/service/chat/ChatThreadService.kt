package com.example.chatbot.service.chat

import com.example.chatbot.entity.chat.ChatThread
import com.example.chatbot.repository.ChatThreadRepository
import com.example.chatbot.util.createPageable
import com.example.chatbot.util.findByIdOrThrow
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime

@Service
@Transactional(readOnly = true)
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

    fun getAllTread(pageIndex: Int, pageSize: Int, orderDirection: String): Page<ChatThread> {
        val pageable = createPageable(pageIndex, pageSize, orderDirection)
        return chatThreadRepository.findAll(pageable)
    }

    fun getThreadsByUser(userId: String, pageIndex: Int, pageSize: Int, orderDirection: String): Page<ChatThread> {
        val pageable = createPageable(pageIndex, pageSize, orderDirection)
        return chatThreadRepository.findByUserId(userId, pageable)
    }

    @Transactional
    fun deleteThread(userId: String, threadId: String) {
        val thread = chatThreadRepository.findByIdOrThrow(threadId)

        if (thread.userId != userId) {
            throw RuntimeException("You are not thread owner")
        }

        chatThreadRepository.delete(thread)
    }

}