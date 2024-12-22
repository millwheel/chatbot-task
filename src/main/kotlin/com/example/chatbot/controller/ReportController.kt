package com.example.chatbot.controller

import com.example.chatbot.dto.report.UserActivityReportResponse
import com.example.chatbot.service.report.UserActivityReportService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/reports")
@RestController
class ReportController (
    private val userActivityReportService: UserActivityReportService
){

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/user-activities/today")
    fun getUserActivityReport() : UserActivityReportResponse {
        return userActivityReportService.getUserActivityReport("KR")
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/chats/today")
    fun getChatReport(){

    }

}