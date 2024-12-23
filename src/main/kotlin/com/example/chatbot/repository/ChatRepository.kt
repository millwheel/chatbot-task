package com.example.chatbot.repository

import com.example.chatbot.entity.chat.Chat
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ChatRepository : JpaRepository<Chat, String> {

    override fun findAll(pageable: Pageable): Page<Chat>

    @Query("select c from Chat c join c.chatThread ct where ct.userId = :userId")
    fun findByUserId(@Param("userId") userId: String, pageable: Pageable): Page<Chat>
}