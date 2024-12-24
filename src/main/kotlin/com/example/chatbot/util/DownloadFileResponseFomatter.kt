package com.example.chatbot.util

import org.springframework.http.*
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

fun constructFileResponse(byteArray: ByteArray, filename: String) : ResponseEntity<ByteArray> {
    val headers = HttpHeaders()
    headers.contentType = MediaType.APPLICATION_OCTET_STREAM
    headers.contentDisposition = constructContentDisposition(filename)
    val finalByteArray = "\uFEFF".toByteArray(Charsets.UTF_8) + byteArray
    return ResponseEntity(finalByteArray, headers, HttpStatus.OK)
}

fun constructContentDisposition(filename: String) : ContentDisposition {
    val encodedFilename = URLEncoder.encode(filename, StandardCharsets.UTF_8).replace("+", "%20")
    return ContentDisposition.attachment().filename(encodedFilename).build()
}