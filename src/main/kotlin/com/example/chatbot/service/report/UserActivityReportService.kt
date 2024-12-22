package com.example.chatbot.service.report

import com.example.chatbot.dto.report.UserActivityReportResponse
import com.example.chatbot.entity.log.UserActivityType
import com.example.chatbot.repository.UserActivityLogRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime
import java.time.ZoneId

@Service
@Transactional(readOnly = true)
class UserActivityReportService (
    private val userActivityLogRepository: UserActivityLogRepository
){

    fun getUserActivityReport(country : String) : UserActivityReportResponse {
        val zoneId = getZoneIdForCountry(country)
        val localDate = OffsetDateTime.now().toLocalDate().atStartOfDay(zoneId).toLocalDate()
        val startTimeOfDay = OffsetDateTime.now().toLocalDate().atStartOfDay(zoneId).toOffsetDateTime()
        val endTimeOfDay = startTimeOfDay.plusDays(1).minusNanos(1)
        val signUpCount = userActivityLogRepository.countByUserActivityTypeAndCreatedAtBetween(
                UserActivityType.SIGNUP,
                startTimeOfDay,
                endTimeOfDay
            )
        val loginCount =
            userActivityLogRepository.countByUserActivityTypeAndCreatedAtBetween(
                UserActivityType.LOGIN,
                startTimeOfDay,
                endTimeOfDay
            )
        val chatCreationCount =
            userActivityLogRepository.countByUserActivityTypeAndCreatedAtBetween(
                UserActivityType.CHAT,
                startTimeOfDay,
                endTimeOfDay
            )
        return UserActivityReportResponse.of(signUpCount, loginCount, chatCreationCount, localDate)
    }

    private fun getZoneIdForCountry(country: String): ZoneId {
        return when (country.uppercase()) {
            "US" -> ZoneId.of("America/New_York")
            "KR" -> ZoneId.of("Asia/Seoul")
            "JP" -> ZoneId.of("Asia/Tokyo")
            "IN" -> ZoneId.of("Asia/Kolkata")
            else -> ZoneId.systemDefault()
        }
    }

}