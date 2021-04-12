package kr.hs.dgsw.orange_market.extension

import kr.hs.dgsw.orange_market.domain.response.base.Response
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

fun Response.toServerResponse(): Mono<ServerResponse> =
    ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(this)