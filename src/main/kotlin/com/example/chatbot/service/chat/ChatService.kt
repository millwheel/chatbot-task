package com.example.chatbot.service.chat

import com.example.chatbot.dto.chat.ChatRequest
import com.example.chatbot.entity.chat.Chat
import com.example.chatbot.repository.ChatRepository
import com.example.chatbot.sender.OpenaiApiSender
import com.example.chatbot.util.createPageable
import com.example.chatbot.util.extractAnswerFromResponse
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import reactor.core.scheduler.Schedulers


@Service
@Transactional(readOnly = true)
class ChatService (
    private val chatThreadService: ChatThreadService,
    private val chatRepository: ChatRepository,
    private val openaiApiSender: OpenaiApiSender
){

    @Transactional
    fun createChat(userId: String, model: String, isStreaming: Boolean, chatRequest: ChatRequest): SseEmitter {
        val chatThread = chatThreadService.getOrCreateThread(userId)
        val question = chatRequest.question

        val emitter = SseEmitter(30_000L)
        if (isStreaming) {
            openaiApiSender.sendRequestAndStreamResponse(question, model)
                .subscribeOn(Schedulers.boundedElastic())
                .doOnNext { chunk ->
                    val answer = extractAnswerFromResponse(chunk)
                    emitter.send(SseEmitter.event().data(answer))
                }
                .doOnComplete {
                    emitter.complete()
                }
                .doOnError { error ->
                    emitter.completeWithError(error)
                }
                .subscribe()
        } else {
            val result = openaiApiSender.sendRequestAndGetResponse(question, model)
            val answer = extractAnswerFromResponse(result)
            emitter.send(SseEmitter.event().data(answer))
            emitter.complete()
        }
        return emitter

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