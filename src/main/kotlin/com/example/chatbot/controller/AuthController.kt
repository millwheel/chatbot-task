package com.example.chatbot.controller

import com.example.chatbot.dto.user.LoginRequest
import com.example.chatbot.dto.user.SignupRequest
import com.example.chatbot.service.log.UserActivityLogService
import com.example.chatbot.service.user.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController (
    val userService: UserService,
    val userActivityLogService: UserActivityLogService
) {

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    fun signUp(@RequestBody signupRequest: SignupRequest) {
        val user = userService.createUser(signupRequest)
        userActivityLogService.createSignUpLog(user.id)
    }

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): String {
        val loginSuccessResponse = userService.login(loginRequest)
        userActivityLogService.createLoginLog(loginSuccessResponse.userId)
        return loginSuccessResponse.token
    }
}
