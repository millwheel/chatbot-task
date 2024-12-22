package com.example.chatbot.repository

import com.example.chatbot.entity.log.UserActivityLogEntity
import com.example.chatbot.entity.log.UserActivityType
import org.springframework.data.jpa.repository.JpaRepository
import java.time.OffsetDateTime

interface UserActivityLogRepository : JpaRepository<UserActivityLogEntity, String> {

    fun countByUserActivityTypeAndCreatedAtBetween(userActivityType: UserActivityType, startTimeOfDay: OffsetDateTime, endTimeOfDay: OffsetDateTime): Int

}