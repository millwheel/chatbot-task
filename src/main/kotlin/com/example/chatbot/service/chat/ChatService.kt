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
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import java.time.Duration


@Service
@Transactional(readOnly = true)
class ChatService (
    private val chatThreadService: ChatThreadService,
    private val chatRepository: ChatRepository,
    private val openaiApiSender: OpenaiApiSender
){

    @Transactional
    fun createChat(userId: String, model: String, isStreaming: Boolean, chatRequest: ChatRequest): Flux<ServerSentEvent<String>> {
//        val chatThread = chatThreadService.getOrCreateThread(userId)
        val question = chatRequest.question

        val responseStream: Flux<ServerSentEvent<String>>
        if (isStreaming) {
            responseStream = openaiApiSender.sendRequestAndStreamResponse(question, model)
                .doOnComplete { println("Stream complete for client") }
                .doOnCancel { println("Stream cancelled by client") }
                .delayElements(Duration.ofMillis(50))
                .map { data ->
                    val answerStream = extractAnswerFromResponseStream(data)
                    ServerSentEvent.builder<String>().data(answerStream).build()
                }
        } else {
            responseStream = Flux.just(openaiApiSender.sendRequestAndGetResponse(question, model))
                .map { data ->
                    val answer = extractAnswerFromResponse(data)
                    ServerSentEvent.builder<String>().data(answer).build()
                }
        }

        return responseStream

//        val chat = Chat(question, answer, chatThread)
//        chatThreadService.updateThreadTimestamp(chatThread)
//        return chatRepository.save(chat)
    }

    fun getAllChats(pageIndex: Int, pageSize: Int, orderDirection: String): Page<Chat> {
        val pageable = createPageable(pageIndex, pageSize, orderDirection)
        return chatRepository.findAll(pageable)
    }

    fun getChatsByUser(userId: String, pageIndex: Int, pageSize: Int, orderDirection: String): Page<Chat> {
        val pageable = createPageable(pageIndex, pageSize, orderDirection)
        return chatRepository.findByUserId(userId, pageable)
    }

}