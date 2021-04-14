package kr.hs.dgsw.orange_market.extension

import kr.hs.dgsw.orange_market.domain.response.base.Response
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpServerErrorException
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

fun Response.toServerResponse(): Mono<ServerResponse> =
    ServerResponse.status(this.status)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(this)

fun Throwable.toServerResponse(): Mono<ServerResponse> =
    when (this) {
        is HttpClientErrorException ->
            Response(this.statusCode, this.message).toServerResponse()

        is HttpServerErrorException ->
            Response(this.statusCode, this.message).toServerResponse()

        else ->
            Response(HttpStatus.INTERNAL_SERVER_ERROR, this.message).toServerResponse()
    }