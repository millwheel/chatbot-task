package com.example.chatbot.service.feedback

import com.example.chatbot.dto.feedback.FeedbackRequest
import com.example.chatbot.dto.feedback.FeedbackResponse
import com.example.chatbot.entity.feedback.Feedback
import com.example.chatbot.repository.ChatRepository
import com.example.chatbot.repository.FeedbackRepository
import com.example.chatbot.util.findByIdOrThrow
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class FeedbackService(
    private val feedbackRepository: FeedbackRepository,
    private val chatRepository: ChatRepository
) {
    fun createFeedback(userId: String, feedbackRequest: FeedbackRequest) {
        val chat = chatRepository.findByIdOrThrow(feedbackRequest.chatId)
        if (feedbackRepository.existsByUserId(userId)) {
            throw RuntimeException("Feedback already exists for this chat by the user.")
        }
        val feedback = Feedback(
            userId = userId,
            chat = chat,
            isPositive = feedbackRequest.isPositive
        )
        feedbackRepository.save(feedback)
    }

    fun getUserFeedbacks(userId: String, isPositive: Boolean?, pageIndex: Int, pageSize: Int, orderDirection: String): Page<FeedbackResponse> {
        val pageable = createPageable(pageIndex, pageSize, orderDirection)
        val feedbacks = isPositive?.let {
            feedbackRepository.findByIsPositiveAndUserId(it, userId, pageable)
        } ?: feedbackRepository.findByUserId(userId, pageable)
        return feedbacks.map { FeedbackResponse.of(it) }
    }

    fun getAllFeedbacks(isPositive: Boolean?, pageIndex: Int, pageSize: Int, orderDirection: String): Page<FeedbackResponse> {
        val pageable = createPageable(pageIndex, pageSize, orderDirection)
        val feedbacks = isPositive?.let {
            feedbackRepository.findByIsPositive(it, pageable)
        } ?: feedbackRepository.findAll(pageable)
        return feedbacks.map { FeedbackResponse.of(it) }
    }

    private fun createPageable(pageIndex: Int, pageSize: Int, orderDirection: String): Pageable {
        val sort = if (orderDirection.lowercase() == "asc") Sort.by("createdAt").ascending()
        else Sort.by("createdAt").descending()
        return PageRequest.of(pageIndex, pageSize, sort)
    }

}