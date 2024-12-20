package com.example.chatbot.exception.handler

import com.example.chatbot.exception.custom.DataNotFoundException
import com.example.chatbot.exception.custom.InvalidEmailOrPasswordException
import com.example.chatbot.exception.dto.ErrorResult
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    private val logger = KotlinLogging.logger {}

    @ExceptionHandler(InvalidEmailOrPasswordException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleInvalidEmailOrPasswordException(ex: InvalidEmailOrPasswordException): ErrorResult {
        logger.error{ ex.message }
        return ErrorResult.of(HttpStatus.UNAUTHORIZED, ex.message!!)
    }

    @ExceptionHandler(DataNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleDataNotFoundException(ex:DataNotFoundException): ErrorResult {
        logger.error{ ex.message }
        return ErrorResult.of(HttpStatus.NOT_FOUND, ex.message!!)
    }

}