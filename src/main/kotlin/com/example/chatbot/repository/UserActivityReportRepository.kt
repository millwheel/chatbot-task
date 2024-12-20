package com.example.chatbot.repository

import com.example.chatbot.entity.report.UserActivityReport
import org.springframework.data.jpa.repository.JpaRepository
import java.time.OffsetDateTime

interface UserActivityReportRepository : JpaRepository<UserActivityReport, String> {

    fun findByDate(date: OffsetDateTime): UserActivityReport?

}