package com.example.chatbot.interceptor

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.json.JSONObject
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import java.util.Base64

@Component
class UserIdInterceptor : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val bearToken = request.getHeader("Authorization") ?: throw RuntimeException("Authorization does not exist")
        val userId = getUserIdFromToken(bearToken)
        request.setAttribute("userId", userId)
        return true
    }

    private fun getUserIdFromToken(jwt: String): String {
        val payloadJson = parseJwt(jwt)
        return payloadJson.getString("sub")
    }

    private fun parseJwt(jwt: String): JSONObject {
        val base64Payload = jwt.split(".")[1]
        val payloadString = String(Base64.getUrlDecoder().decode(base64Payload))
        return JSONObject(payloadString)
    }
}