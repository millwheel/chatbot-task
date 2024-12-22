package com.example.chatbot.service.log

import com.example.chatbot.entity.log.UserActivityLog
import com.example.chatbot.repository.UserActivityLogRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserActivityLogService (
    val userActivityLogRepository: UserActivityLogRepository
) {

    fun createSignUpLog(userId: String, email: String) {
        val signUpLog = UserActivityLog.ofSignUp(userId, email)
        userActivityLogRepository.save(signUpLog)
    }

    fun createLoginLog(userId: String, email: String) {
        val loginLog = UserActivityLog.ofLogin(userId, email)
        userActivityLogRepository.save(loginLog)
    }

    fun createChatCreationLog(userId: String, email: String) {
        val chatLog = UserActivityLog.ofChat(userId, email)
        userActivityLogRepository.save(chatLog)
    }

}