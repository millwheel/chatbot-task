package com.example.chatbot.controller

import com.example.chatbot.dto.feedback.FeedbackRequest
import com.example.chatbot.dto.feedback.FeedbackResponse
import com.example.chatbot.service.feedback.FeedbackService
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/feedbacks")
class FeedbackController(
    private val feedbackService: FeedbackService
) {

    // TODO 관리자 권한 추가
    @GetMapping("/all")
    fun getAllFeedbacks(
        @RequestParam isPositive: Boolean?,
        @RequestParam(defaultValue = "0") pageIndex: Int,
        @RequestParam(defaultValue = "10") pageSize: Int,
        @RequestParam(defaultValue = "desc") orderDirection: String
    ): Page<FeedbackResponse> {
        return feedbackService.getAllFeedbacks(isPositive, pageIndex, pageSize, orderDirection)
    }

    // TODO 멤버 권한 추가
    @GetMapping("/me")
    fun getUserFeedbacks(
        @RequestAttribute userId: String,
        @RequestParam isPositive: Boolean?,
        @RequestParam(defaultValue = "0") pageIndex: Int,
        @RequestParam(defaultValue = "10") pageSize: Int,
        @RequestParam(defaultValue = "desc") orderDirection: String
        ): Page<FeedbackResponse> {
        return feedbackService.getUserFeedbacks(userId, isPositive, pageIndex, pageSize, orderDirection)
    }

    @PostMapping
    fun createFeedback(
        @RequestAttribute userId: String,
        @RequestBody feedbackRequest: FeedbackRequest
    ) {
        feedbackService.createFeedback(userId, feedbackRequest)
    }

}
