package com.example.chatbot.controller

import com.example.chatbot.dto.user.LoginRequest
import com.example.chatbot.dto.user.SignupRequest
import com.example.chatbot.service.UserService
import com.example.chatbot.util.generateToken
import com.example.chatbot.util.matchesPassword
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController (
    val userService: UserService
) {

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    fun signUp(@RequestBody signupRequest: SignupRequest) {
        userService.createUser(signupRequest)
    }

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): String {
        return userService.login(loginRequest)
    }
}
