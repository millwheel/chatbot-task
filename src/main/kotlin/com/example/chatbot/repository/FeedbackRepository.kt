package com.example.chatbot.repository

import com.example.chatbot.entity.feedback.Feedback
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface FeedbackRepository : JpaRepository<Feedback, String> {
    fun existsByUserId(userId: String): Boolean
    fun findByUserId(userId: String, pageable: Pageable): Page<Feedback>
    fun findByIsPositiveAndUserId(isPositive: Boolean, userId: String, pageable: Pageable): Page<Feedback>
    fun findByIsPositive(isPositive: Boolean, pageable: Pageable): Page<Feedback>
}