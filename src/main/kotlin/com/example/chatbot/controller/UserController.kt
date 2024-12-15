package com.example.chatbot.controller

import com.example.chatbot.entity.User
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
    fun getUsers() : List<User> {
        return userService.getAllUsers()
    }

}