package com.example.chatbot.exception.handler

import com.example.chatbot.exception.custom.DataNotFoundException
import com.example.chatbot.exception.custom.InvalidEmailOrPasswordException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class GlobalExceptionHandler {


    @ExceptionHandler(InvalidEmailOrPasswordException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleInvalidEmailOrPasswordException(ex: InvalidEmailOrPasswordException): ResponseEntity<String> {
        return ResponseEntity(ex.message, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(DataNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleDataNotFoundException(ex:DataNotFoundException): ResponseEntity<String> {
        return ResponseEntity(ex.message, HttpStatus.NOT_FOUND)
    }

}