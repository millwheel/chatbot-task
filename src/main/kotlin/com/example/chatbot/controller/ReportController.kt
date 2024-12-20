package com.example.chatbot.controller

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/report")
@RestController
class ReportController {

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/user-activities/today")
    fun getUserActivityReport() {

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/chats/today")
    fun getChatReport(){

    }

}