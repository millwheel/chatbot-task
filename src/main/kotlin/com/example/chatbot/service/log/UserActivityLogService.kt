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

    fun createLoginLog(userId: String) {
        val loginLog = UserActivityLog.ofLogin(userId)
        userActivityLogRepository.save(loginLog)
    }

    fun createSignUpLog(userId: String) {
        val signUpLog = UserActivityLog.ofSignUp(userId)
        userActivityLogRepository.save(signUpLog)
    }

    fun createChatCreationLog(userId: String) {
        val chatLog = UserActivityLog.ofChat(userId)
        userActivityLogRepository.save(chatLog)
    }

}