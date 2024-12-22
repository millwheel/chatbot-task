package com.example.chatbot.repository

import com.example.chatbot.entity.log.ChatLog
import org.springframework.data.jpa.repository.JpaRepository
import java.time.OffsetDateTime

interface ChatLogRepository : JpaRepository<ChatLog, String> {

    fun findByCreatedAtBetween(startTimeOfDay: OffsetDateTime, endTimeOfDay: OffsetDateTime): List<ChatLog>

}