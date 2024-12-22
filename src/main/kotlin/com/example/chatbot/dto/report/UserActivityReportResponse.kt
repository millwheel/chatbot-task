package com.example.chatbot.dto.report

import java.time.LocalDate

data class UserActivityReportResponse (
    val signUpCount: Int,
    val loginCount: Int,
    val chatCreationCount: Int,
    val date : LocalDate
) {
    companion object {
        fun of (signUpCount: Int, loginCount: Int, chatCreationCount:Int, localDate: LocalDate): UserActivityReportResponse {
            return UserActivityReportResponse(
                signUpCount = signUpCount,
                loginCount = loginCount,
                chatCreationCount = chatCreationCount,
                date = localDate
            )
        }
    }
}