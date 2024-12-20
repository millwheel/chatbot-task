package com.example.chatbot.controller

import com.example.chatbot.entity.report.UserActivityReport
import com.example.chatbot.service.UserActivityReportService
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
    fun getUserActivityReport() : UserActivityReport {
        return userActivityReportService.getUserActivityReport()
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/chats/today")
    fun getChatReport(){

    }

}