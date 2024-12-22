package com.example.chatbot.service.report

import com.example.chatbot.dto.report.UserActivityReportResponse
import com.example.chatbot.entity.log.UserActivityType
import com.example.chatbot.repository.UserActivityLogRepository
import com.example.chatbot.util.getStartAndEndTimeOfDayByCountryCode
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserActivityReportService (
    private val userActivityLogRepository: UserActivityLogRepository
){

    fun getUserActivityReport(country : String) : UserActivityReportResponse {
        val (startTimeOfDay, endTimeOfDay) = getStartAndEndTimeOfDayByCountryCode(country)
        val localTodayDate = startTimeOfDay.toLocalDate()
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
        return UserActivityReportResponse.of(signUpCount, loginCount, chatCreationCount, localTodayDate)
    }


}