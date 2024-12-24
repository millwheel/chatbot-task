package com.example.chatbot.controller

import com.example.chatbot.dto.report.ChatReportResponse
import com.example.chatbot.dto.report.UserActivityReportResponse
import com.example.chatbot.service.report.ChatReportService
import com.example.chatbot.service.report.UserActivityReportService
import com.example.chatbot.util.constructFileResponse
import com.example.chatbot.util.extractCountryFromHeader
import org.springframework.http.*
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
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
    fun getUserActivityReport(@RequestHeader("Accept-Language") acceptLanguage: String) : UserActivityReportResponse {
        val countryCode = extractCountryFromHeader(acceptLanguage)
        return userActivityReportService.getUserActivityReport(countryCode)
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/chats/today")
    fun getChatReport(@RequestHeader("Accept-Language") acceptLanguage: String) : ChatReportResponse {
        val countryCode = extractCountryFromHeader(acceptLanguage)
        return chatReportLogService.getChatReport(countryCode)
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/chats/today/csv")
    fun getChatReportAsCsv(@RequestHeader("Accept-Language") acceptLanguage: String) : ResponseEntity<ByteArray> {
        val countryCode = extractCountryFromHeader(acceptLanguage)
        val chatReportResponse = chatReportLogService.getChatReport(countryCode)
        val csvContentByteArray = buildString {
            append("question,answer,userEmail\n")
            chatReportResponse.logs.forEach {
                append("\"${it.question}\",\"${it.answer}\",\"${it.userEmail}\"\n")
            }
        }.toByteArray(Charsets.UTF_8)
        val filename = "chat_report_${chatReportResponse.date}.csv"
        return constructFileResponse(csvContentByteArray, filename)
    }

}