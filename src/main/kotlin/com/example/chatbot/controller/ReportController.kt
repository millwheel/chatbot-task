package com.example.chatbot.controller

import com.example.chatbot.dto.report.ChatReportResponse
import com.example.chatbot.dto.report.UserActivityReportResponse
import com.example.chatbot.service.report.ChatReportService
import com.example.chatbot.service.report.UserActivityReportService
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/reports")
@RestController
class ReportController (
    private val userActivityReportService: UserActivityReportService,
    private val chatReportLogService: ChatReportService
){

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/user-activities/today")
    fun getUserActivityReport() : UserActivityReportResponse {
        return userActivityReportService.getUserActivityReport("KR")
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/chats/today")
    fun getChatReport() : ChatReportResponse {
        return chatReportLogService.getChatReport("KR")
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/chats/today/csv")
    fun getChatReportAsCsv(response: HttpServletResponse) {
        val chatReport = chatReportLogService.getChatReport("KR")
        response.setHeader("Content-Type", "text/csv; charset=UTF-8")
        response.setHeader("Content-Disposition", "attachment; filename=\"chat_report_${chatReport.date}.csv\"")
        response.writer.use { writer ->
            writer.append("Question,Answer,User Email\n")
            chatReport.logs.forEach {
                writer.append("${it.question},${it.answer},${it.userEmail}\n")
            }
        }
    }

}