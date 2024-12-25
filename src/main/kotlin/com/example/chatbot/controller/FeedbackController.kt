package com.example.chatbot.controller

import com.example.chatbot.config.security.component.CustomPrincipal
import com.example.chatbot.dto.feedback.FeedbackCreateRequest
import com.example.chatbot.dto.feedback.FeedbackResponse
import com.example.chatbot.dto.feedback.FeedbackUpdateRequest
import com.example.chatbot.entity.feedback.FeedbackStatus
import com.example.chatbot.service.feedback.FeedbackService
import com.example.chatbot.validator.PaginationValidator
import org.springframework.data.domain.Page
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/feedbacks")
class FeedbackController(
    private val feedbackService: FeedbackService,
    private val paginationValidator: PaginationValidator
) {

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/all")
    fun getAllFeedbacks(
        @RequestParam isPositive: Boolean?,
        @RequestParam(defaultValue = "0") pageIndex: Int,
        @RequestParam(defaultValue = "10") pageSize: Int,
        @RequestParam(defaultValue = "desc") orderDirection: String
    ): Page<FeedbackResponse> {
        paginationValidator.validateParameter(pageIndex, pageSize, orderDirection)
        return feedbackService.getAllFeedbacks(isPositive, pageIndex, pageSize, orderDirection)
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
    @GetMapping("/me")
    fun getUserFeedbacks(
        @AuthenticationPrincipal principal: CustomPrincipal,
        @RequestParam isPositive: Boolean?,
        @RequestParam(defaultValue = "0") pageIndex: Int,
        @RequestParam(defaultValue = "10") pageSize: Int,
        @RequestParam(defaultValue = "desc") orderDirection: String
        ): Page<FeedbackResponse> {
        paginationValidator.validateParameter(pageIndex, pageSize, orderDirection)
        return feedbackService.getUserFeedbacks(principal.userId, isPositive, pageIndex, pageSize, orderDirection)
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
    @PostMapping
    fun createFeedback(
        @AuthenticationPrincipal principal: CustomPrincipal,
        @RequestBody feedbackCreateRequest: FeedbackCreateRequest
    ) {
        feedbackService.createFeedback(principal.userId, feedbackCreateRequest)
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/{feedbackId}/status/pending")
    fun updateFeedbackStatusAsPending(
        @PathVariable feedbackId: String
    ) {
        feedbackService.updateFeedbackStatus(feedbackId, FeedbackStatus.PENDING)
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/{feedbackId}/status/resolved")
    fun updateFeedbackStatusAsResolved(
        @PathVariable feedbackId: String
    ) {
        feedbackService.updateFeedbackStatus(feedbackId, FeedbackStatus.RESOLVED)
    }

}
