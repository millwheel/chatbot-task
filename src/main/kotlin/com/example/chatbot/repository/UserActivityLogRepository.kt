package com.example.chatbot.repository

import com.example.chatbot.entity.log.UserActivityLog
import com.example.chatbot.entity.log.UserActivityType
import org.springframework.data.jpa.repository.JpaRepository

interface UserActivityLogRepository : JpaRepository<UserActivityLog, String> {

    fun findByUserActivityType(userActivityType: UserActivityType)

}