package com.example.chatbot.controller

import com.example.chatbot.dto.feedback.FeedbackCreateRequest
import com.example.chatbot.dto.feedback.FeedbackResponse
import com.example.chatbot.dto.feedback.FeedbackUpdateRequest
import com.example.chatbot.service.feedback.FeedbackService
import org.springframework.data.domain.Page
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/feedbacks")
class FeedbackController(
    private val feedbackService: FeedbackService
) {

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/all")
    fun getAllFeedbacks(
        @RequestParam isPositive: Boolean?,
        @RequestParam(defaultValue = "0") pageIndex: Int,
        @RequestParam(defaultValue = "10") pageSize: Int,
        @RequestParam(defaultValue = "desc") orderDirection: String
    ): Page<FeedbackResponse> {
        return feedbackService.getAllFeedbacks(isPositive, pageIndex, pageSize, orderDirection)
    }

    @PreAuthorize("hasAnyRole('ADMIN, MEMBER')")
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

    @PreAuthorize("hasAnyRole('ADMIN, MEMBER')")
    @PostMapping
    fun createFeedback(
        @RequestAttribute userId: String,
        @RequestBody feedbackCreateRequest: FeedbackCreateRequest
    ) {
        feedbackService.createFeedback(userId, feedbackCreateRequest)
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/{feedbackId}")
    fun updateFeedbackStatus(
        @PathVariable feedbackId: String,
        @RequestBody feedbackUpdateRequest: FeedbackUpdateRequest
    ) {
        feedbackService.updateFeedbackStatus(feedbackId, feedbackUpdateRequest.status)
    }

}
