package com.example.chatbot.dto.feedback

import com.example.chatbot.entity.feedback.Feedback
import com.example.chatbot.entity.feedback.FeedbackStatus
import java.time.OffsetDateTime

data class FeedbackResponse (
    val id: String,
    val userId: String,
    val chatId: String,
    val isPositive: Boolean,
    val createdAt: OffsetDateTime,
    val status: FeedbackStatus
) {
    companion object {
        fun of (feedback: Feedback): FeedbackResponse {
            return FeedbackResponse(
                id = feedback.id,
                userId = feedback.userId,
                chatId = feedback.chat.id,
                isPositive = feedback.isPositive,
                createdAt = feedback.createdAt,
                status = feedback.status
            )
        }
    }
}