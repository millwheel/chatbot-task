package com.example.chatbot.service

import com.example.chatbot.entity.report.UserActivityReport
import com.example.chatbot.repository.UserActivityReportRepository
import org.springframework.stereotype.Service
import java.time.OffsetDateTime

@Service
class UserActivityReportService (
    private val userActivityReportRepository: UserActivityReportRepository
){

    fun getUserActivityReport(): UserActivityReport {
        val userActivityReport = userActivityReportRepository.findByDate(OffsetDateTime.now())
            ?: throw RuntimeException("There is no user activity found. something went wrong")

        return userActivityReport
    }

}