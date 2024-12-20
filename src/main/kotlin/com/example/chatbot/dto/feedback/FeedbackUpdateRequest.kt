package com.example.chatbot.dto.feedback

import com.example.chatbot.entity.feedback.FeedbackStatus
import org.jetbrains.annotations.NotNull

data class FeedbackUpdateRequest(
    @field:NotNull
    val status: FeedbackStatus
)
