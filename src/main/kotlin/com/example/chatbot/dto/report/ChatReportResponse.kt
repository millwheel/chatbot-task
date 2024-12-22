package com.example.chatbot.dto.report

import java.time.LocalDate

data class ChatReportResponse(
    val date: LocalDate,
    val logs: List<ChatReportLogResponse>
) {
    companion object {
        fun of(localTodayDate: LocalDate, chatReportLogResponses: List<ChatReportLogResponse>): ChatReportResponse {
            return ChatReportResponse(localTodayDate, chatReportLogResponses)
        }
    }
}
