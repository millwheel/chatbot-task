package com.example.chatbot.sender.openai

import com.example.chatbot.sender.OpenaiApiSender
import com.example.chatbot.util.extractAnswerFromResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import reactor.core.scheduler.Schedulers
import java.util.concurrent.CountDownLatch
import kotlin.test.Test

@SpringBootTest
@ActiveProfiles("local")
class OpenaiApiSenderTest (
    @Autowired
    private val openaiApiSender: OpenaiApiSender
){

    @Test
    fun testOpenAiApi() {
        val response = openaiApiSender.sendRequestAndGetResponse("1+1은 뭐야?", "gpt-4o-mini")
        val answer = extractAnswerFromResponse(response)
        print(answer)
    }

    @Test
    fun testOpenAiApiWithStream() {
        val latch = CountDownLatch(1)
        openaiApiSender.sendRequestAndStreamResponse("1+1은 뭐야?", "gpt-4o-mini")
            .subscribeOn(Schedulers.boundedElastic())
            .doOnNext { chunk ->
                println(chunk)
            }
            .doOnComplete {
                latch.countDown()
            }
            .doOnError { error ->
                println("Error: ${error.message}")
                latch.countDown()
            }
            .subscribe()

        latch.await()
    }

}