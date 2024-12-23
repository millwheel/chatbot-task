package com.example.chatbot.controller

import com.example.chatbot.dto.report.ChatReportResponse
import com.example.chatbot.dto.report.UserActivityReportResponse
import com.example.chatbot.service.report.ChatReportService
import com.example.chatbot.service.report.UserActivityReportService
import com.example.chatbot.util.constructFileResponse
import org.springframework.http.*
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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/chats/today/csv")
    fun getChatReportAsCsv() : ResponseEntity<ByteArray> {
        val chatReportResponse = chatReportLogService.getChatReport("KR")
        val filename = "chat_report_${chatReportResponse.date}.csv"
        val csvContent = buildString {
            append("question,answer,userEmail\n")
            chatReportResponse.logs.forEach {
                append("\"${it.question}\",\"${it.answer}\",\"${it.userEmail}\"\n")
            }
        }
        val byteArray = csvContent.toByteArray(Charsets.UTF_8)
        return constructFileResponse(byteArray, filename)
    }

}