package com.example.chatbot.service.chat

import com.example.chatbot.dto.chat.ChatRequest
import com.example.chatbot.entity.chat.Chat
import com.example.chatbot.repository.ChatRepository
import com.example.chatbot.sender.OpenaiApiSender
import com.example.chatbot.util.createPageable
import com.example.chatbot.util.extractAnswerFromResponse
import com.example.chatbot.util.extractAnswerFromResponseStream
import org.springframework.data.domain.Page
import org.springframework.http.codec.ServerSentEvent
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import java.time.Duration


@Service
class ChatService (
    private val chatThreadService: ChatThreadService,
    private val chatRepository: ChatRepository,
    private val openaiApiSender: OpenaiApiSender
){

    fun createAnswer(userId: String, model: String, isStreaming: Boolean, chatRequest: ChatRequest): Flux<ServerSentEvent<String>> {
        val question = chatRequest.question
        val responseStream: Flux<ServerSentEvent<String>>
        if (isStreaming) {
            responseStream = openaiApiSender.sendRequestAndStreamResponse(question, model)
                .doOnNext { data -> println("Data: $data") }
                .doOnComplete { println("Stream complete for client") }
                .doOnCancel { println("Stream cancelled by client") }
                .delayElements(Duration.ofMillis(50))
                .map { data ->
                    val answerStream = extractAnswerFromResponseStream(data)
                    ServerSentEvent.builder<String>().data(answerStream).build()
                }
        } else {
            responseStream = Flux.just(openaiApiSender.sendRequestAndGetResponse(question, model))
                .doOnNext { data -> println("Data: $data") }
                .map { data ->
                    val answer = extractAnswerFromResponse(data)
                    ServerSentEvent.builder<String>().data(answer).build()
                }
        }
        return responseStream
    }

    @Transactional
    fun createChat(userId: String, question: String, answer: String): Chat {
        val chatThread = chatThreadService.getOrCreateThread(userId)
        val chat = Chat(question, answer, chatThread)
        chatThreadService.updateThreadTimestamp(chatThread)
        return chatRepository.save(chat)
    }

    @Transactional(readOnly = true)
    fun getAllChats(pageIndex: Int, pageSize: Int, orderDirection: String): Page<Chat> {
        val pageable = createPageable(pageIndex, pageSize, orderDirection)
        return chatRepository.findAll(pageable)
    }

    @Transactional(readOnly = true)
    fun getChatsByUser(userId: String, pageIndex: Int, pageSize: Int, orderDirection: String): Page<Chat> {
        val pageable = createPageable(pageIndex, pageSize, orderDirection)
        return chatRepository.findByUserId(userId, pageable)
    }

}