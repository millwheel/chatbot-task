package com.example.chatbot.service.report

import com.example.chatbot.dto.report.ChatReportLogResponse
import com.example.chatbot.dto.report.ChatReportResponse
import com.example.chatbot.repository.ChatLogRepository
import com.example.chatbot.util.getStartAndEndTimeOfDayByCountryCode
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ChatReportService (
    private val chatLogRepository: ChatLogRepository
){

    fun getChatReport(countryCode: String) : ChatReportResponse {
        val (startTimeOfDay, endTimeOfDay) = getStartAndEndTimeOfDayByCountryCode(countryCode)
        val localTodayDate = startTimeOfDay.toLocalDate()
        val chatLogs = chatLogRepository.findByCreatedAtBetween(startTimeOfDay, endTimeOfDay)
        val chatReportLogResponses = chatLogs.map {
            ChatReportLogResponse(it.question, it.answer, it.email)
        }
        return ChatReportResponse(localTodayDate, chatReportLogResponses)
    }

}