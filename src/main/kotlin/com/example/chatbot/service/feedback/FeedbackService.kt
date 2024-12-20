package com.example.chatbot.service.feedback

import com.example.chatbot.dto.feedback.FeedbackCreateRequest
import com.example.chatbot.dto.feedback.FeedbackResponse
import com.example.chatbot.entity.feedback.Feedback
import com.example.chatbot.entity.feedback.FeedbackStatus
import com.example.chatbot.repository.ChatRepository
import com.example.chatbot.repository.FeedbackRepository
import com.example.chatbot.util.createPageable
import com.example.chatbot.util.findByIdOrThrow
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class FeedbackService(
    private val feedbackRepository: FeedbackRepository,
    private val chatRepository: ChatRepository
) {

    @Transactional
    fun createFeedback(userId: String, feedbackCreateRequest: FeedbackCreateRequest) {
        val chat = chatRepository.findByIdOrThrow(feedbackCreateRequest.chatId)
        if (feedbackRepository.existsByUserId(userId)) {
            throw RuntimeException("Feedback already exists for this chat by the user.")
        }
        val feedback = Feedback(
            userId = userId,
            chat = chat,
            isPositive = feedbackCreateRequest.isPositive
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

    @Transactional
    fun updateFeedbackStatus(feedbackId: String, feedbackStatus: FeedbackStatus){
        val feedback = feedbackRepository.findByIdOrThrow(feedbackId)
        feedback.updateStatus(feedbackStatus)
    }

}