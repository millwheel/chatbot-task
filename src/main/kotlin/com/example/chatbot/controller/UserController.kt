package com.example.chatbot.controller

import com.example.chatbot.dto.ResponseResult
import com.example.chatbot.dto.user.UserResponse
import com.example.chatbot.entity.user.User
import com.example.chatbot.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController (
    val userService: UserService
) {

    @GetMapping
    fun getUsers() : ResponseResult<List<UserResponse>> {
        return ResponseResult(userService.getAllUsers())
    }

}