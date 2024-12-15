package com.example.chatbot.repository

import com.example.chatbot.entity.feedback.Feedback
import org.springframework.data.jpa.repository.JpaRepository

interface FeedbackRepository : JpaRepository<Feedback, String> {
}