package com.example.chatbot.exception.handler

import com.example.chatbot.exception.custom.BadRequestException
import com.example.chatbot.exception.custom.DataNotFoundException
import com.example.chatbot.exception.custom.InvalidEmailOrPasswordException
import com.example.chatbot.exception.custom.NotThreadOwnerException
import com.example.chatbot.exception.dto.ErrorResult
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    private val logger = KotlinLogging.logger {}

    @ExceptionHandler(RuntimeException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleRuntimeException(ex: RuntimeException): ErrorResult {
        logger.error{ ex.message }
        if (ex.message == "Access Denied") {
            return ErrorResult.of(HttpStatus.FORBIDDEN, "You do not have permission to access this resource")
        }
        return ErrorResult.of(HttpStatus.INTERNAL_SERVER_ERROR, ex.message!!)
    }

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

    @ExceptionHandler(NotThreadOwnerException::class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun handleNotThreadOwnerException(ex: NotThreadOwnerException): ErrorResult {
        logger.error{ ex.message }
        return ErrorResult.of(HttpStatus.FORBIDDEN, ex.message!!)
    }

    @ExceptionHandler(BadRequestException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBadRequestException(ex: BadRequestException): ErrorResult {
        logger.error{ ex.message }
        return ErrorResult.of(HttpStatus.BAD_REQUEST, ex.message!!)
    }

}