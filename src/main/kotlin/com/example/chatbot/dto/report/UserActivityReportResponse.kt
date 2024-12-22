package com.example.chatbot.dto.report

import java.time.LocalDate

data class UserActivityReportResponse (
    val signUp: Int,
    val login: Int,
    val chatCreation: Int,
    val date : LocalDate
) {
    companion object {
        fun of (signUpCount: Int, loginCount: Int, chatCreationCount:Int, localDate: LocalDate): UserActivityReportResponse {
            return UserActivityReportResponse(
                signUp = signUpCount,
                login = loginCount,
                chatCreation = chatCreationCount,
                date = localDate
            )
        }
    }
}