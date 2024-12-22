package com.example.chatbot.service.log

import com.example.chatbot.entity.log.UserActivityLogEntity
import com.example.chatbot.repository.UserActivityLogRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserActivityLogService (
    val userActivityLogRepository: UserActivityLogRepository
) {

    fun createSignUpLog(userId: String, email: String) {
        val signUpLog = UserActivityLogEntity.ofSignUp(userId, email)
        userActivityLogRepository.save(signUpLog)
    }

    fun createLoginLog(userId: String, email: String) {
        val loginLog = UserActivityLogEntity.ofLogin(userId, email)
        userActivityLogRepository.save(loginLog)
    }

    fun createChatCreationLog(userId: String, email: String) {
        val chatLog = UserActivityLogEntity.ofChat(userId, email)
        userActivityLogRepository.save(chatLog)
    }

}