package com.example.chatbot.service.report

import com.example.chatbot.dto.report.ChatReportResponse
import com.example.chatbot.repository.ChatLogRepository
import com.example.chatbot.util.getStartAndEndTimeOfDayByCountryCode
import org.springframework.stereotype.Service

@Service
class ChatReportService (
    private val chatLogRepository: ChatLogRepository
){

    fun getChatReport(countryCode: String) : List<ChatReportResponse> {
        val (startTimeOfDay, endTimeOfDay) = getStartAndEndTimeOfDayByCountryCode(countryCode)
        val localTodayDate = startTimeOfDay.toLocalDate()
        val chatLogs = chatLogRepository.findByCreatedAtBetween(startTimeOfDay, endTimeOfDay)
        val chatReportResponses = chatLogs.map {
            ChatReportResponse(it.question, it.answer, it.email)
        }
        return chatReportResponses
    }

}