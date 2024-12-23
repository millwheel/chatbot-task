package com.example.chatbot.sender.openai

import com.example.chatbot.sender.OpenaiApiSender
import com.example.chatbot.util.parseAnswerFromResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import kotlin.test.Test

@SpringBootTest
@ActiveProfiles("local")
class OpenaiApiSenderTest (
    @Autowired
    private val openaiApiSender: OpenaiApiSender
){

    @Test
    fun testOpenAiApi() {
        val response = openaiApiSender.sendRequestAndGetResponse("Kotlin은 무슨 이유로 만들어졌지?", "gpt-4o-mini")
        val answer = parseAnswerFromResponse(response)
        print(answer)
    }

}