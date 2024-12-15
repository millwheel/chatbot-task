package com.example.chatbot.repository

import com.example.chatbot.entity.chat.ChatThread
import org.springframework.data.jpa.repository.JpaRepository

interface ChatThreadRepository : JpaRepository<ChatThread, Long> {
    fun findByUserIdOrderByUpdatedAtDesc(userId: String): List<ChatThread>
}