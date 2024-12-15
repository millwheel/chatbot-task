package com.example.chatbot.repository

import com.example.chatbot.entity.chat.ChatThread
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface ChatThreadRepository : JpaRepository<ChatThread, Long> {
    fun findByUserIdOrderByUpdatedAtDesc(userId: String): List<ChatThread>
    override fun findAll(pageable: Pageable): Page<ChatThread>
    fun findByUserId(userId: String, pageable: Pageable): Page<ChatThread>
}