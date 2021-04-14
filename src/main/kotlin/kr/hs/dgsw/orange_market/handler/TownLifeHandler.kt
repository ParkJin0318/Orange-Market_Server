package kr.hs.dgsw.orange_market.handler

import kr.hs.dgsw.orange_market.domain.request.town.TownLifeRequest
import kr.hs.dgsw.orange_market.domain.response.base.Response
import kr.hs.dgsw.orange_market.domain.response.base.ResponseData
import kr.hs.dgsw.orange_market.extension.toServerResponse
import kr.hs.dgsw.orange_market.service.town.TownLifeServiceImpl
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class TownLifeHandler(
    private val townLifeService: TownLifeServiceImpl
) {
    fun getAll(request: ServerRequest): Mono<ServerResponse> =
        Mono.justOrEmpty(request.queryParam("city"))
            .switchIfEmpty(Mono.error(Exception("Bad Request")))
            .flatMap(townLifeService::getAllTownLife)
            .flatMap { ResponseData("조회 성공", it).toServerResponse() }
            .onErrorResume { Response(it.message).toServerResponse() }

    fun get(request: ServerRequest): Mono<ServerResponse> =
        Mono.justOrEmpty(request.pathVariable("idx").toInt())
            .switchIfEmpty(Mono.error(Exception("Bad Request")))
            .flatMap(townLifeService::getTownLife)
            .flatMap { ResponseData("조회 성공", it).toServerResponse() }
            .onErrorResume { Response(it.message).toServerResponse() }

    fun getAllComment(request: ServerRequest): Mono<ServerResponse> =
        Mono.justOrEmpty(request.pathVariable("idx").toInt())
            .switchIfEmpty(Mono.error(Exception("Bad Request")))
            .flatMap(townLifeService::getAllTownLifeComment)
            .flatMap { ResponseData("조회 성공", it).toServerResponse() }
            .onErrorResume { Response(it.message).toServerResponse() }

    fun save(request: ServerRequest): Mono<ServerResponse> =
        request.bodyToMono(TownLifeRequest::class.java)
            .switchIfEmpty(Mono.error(Exception("Bad Request")))
            .flatMap(townLifeService::saveTownLife)
            .switchIfEmpty(Mono.error(Exception("등록 실패")))
            .flatMap { Response("등록 성공").toServerResponse() }
            .onErrorResume { Response(it.message).toServerResponse() }

    fun update(request: ServerRequest): Mono<ServerResponse> =
        request.bodyToMono(TownLifeRequest::class.java)
            .switchIfEmpty(Mono.error(Exception("Bad Request")))
            .flatMap { townLifeRequest ->
                val idx = request.pathVariable("idx").toInt()
                townLifeService.updateTownLife(idx, townLifeRequest)
            }.switchIfEmpty(Mono.error(Exception("업데이트 실패")))
            .flatMap { Response("업데이트 성공").toServerResponse() }
            .onErrorResume { Response(it.message).toServerResponse() }

    fun delete(request: ServerRequest): Mono<ServerResponse> =
        Mono.justOrEmpty(request.pathVariable("idx").toInt())
            .switchIfEmpty(Mono.error(Exception("Bad Request")))
            .flatMap(townLifeService::deleteTownLife)
            .flatMap { Response("삭제 성공").toServerResponse() }
            .onErrorResume { Response(it.message).toServerResponse() }
}