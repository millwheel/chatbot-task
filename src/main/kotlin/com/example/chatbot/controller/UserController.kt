package com.example.chatbot.controller

import com.example.chatbot.dto.ResponseResult
import com.example.chatbot.dto.user.UserResponse
import com.example.chatbot.service.user.UserService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController (
    val userService: UserService
) {

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    fun getUsers() : ResponseResult<List<UserResponse>> {
        return ResponseResult(userService.getAllUsers())
    }

}