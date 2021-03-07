package kr.hs.dgsw.orange_market.exception

import kr.hs.dgsw.orange_market.domain.model.response.Response
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpServerErrorException

@ControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(HttpClientErrorException::class)
    fun handlerHttpClientErrorException(e: HttpClientErrorException): ResponseEntity<Response> {
        val data = Response(e.statusCode, e.message)
        return ResponseEntity<Response>(data, e.statusCode)
    }

    @ExceptionHandler(HttpServerErrorException::class)
    fun handlerHttpServerErrorException(e: HttpServerErrorException): ResponseEntity<Response> {
        val data = Response(e.statusCode, e.message)
        return ResponseEntity<Response>(data, e.statusCode)
    }
}