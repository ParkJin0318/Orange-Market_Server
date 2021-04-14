package kr.hs.dgsw.orange_market.handler

import kr.hs.dgsw.orange_market.domain.request.town.TownLifeRequest
import kr.hs.dgsw.orange_market.domain.response.base.Response
import kr.hs.dgsw.orange_market.domain.response.base.ResponseData
import kr.hs.dgsw.orange_market.extension.toServerResponse
import kr.hs.dgsw.orange_market.service.town.TownLifeServiceImpl
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class TownLifeHandler(
    private val townLifeService: TownLifeServiceImpl
) {
    fun getAll(request: ServerRequest): Mono<ServerResponse> =
        Mono.justOrEmpty(request.queryParam("city"))
            .switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.BAD_REQUEST, "잚못된 요청")))
            .flatMap(townLifeService::getAllTownLife)
            .flatMap { ResponseData(HttpStatus.OK,"조회 성공", it).toServerResponse() }
            .onErrorResume { it.toServerResponse() }

    fun get(request: ServerRequest): Mono<ServerResponse> =
        Mono.justOrEmpty(request.pathVariable("idx").toInt())
            .switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.BAD_REQUEST, "잚못된 요청")))
            .flatMap(townLifeService::getTownLife)
            .flatMap { ResponseData(HttpStatus.OK,"조회 성공", it).toServerResponse() }
            .onErrorResume { it.toServerResponse() }

    fun getAllComment(request: ServerRequest): Mono<ServerResponse> =
        Mono.justOrEmpty(request.pathVariable("idx").toInt())
            .switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.BAD_REQUEST, "잚못된 요청")))
            .flatMap(townLifeService::getAllTownLifeComment)
            .flatMap { ResponseData(HttpStatus.OK,"조회 성공", it).toServerResponse() }
            .onErrorResume { it.toServerResponse() }

    fun save(request: ServerRequest): Mono<ServerResponse> =
        request.bodyToMono(TownLifeRequest::class.java)
            .switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.BAD_REQUEST, "잚못된 요청")))
            .flatMap(townLifeService::saveTownLife)
            .switchIfEmpty(Mono.error(Exception("등록 실패")))
            .flatMap { Response(HttpStatus.OK,"등록 성공").toServerResponse() }
            .onErrorResume { it.toServerResponse() }

    fun update(request: ServerRequest): Mono<ServerResponse> =
        request.bodyToMono(TownLifeRequest::class.java)
            .switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.BAD_REQUEST, "잚못된 요청")))
            .flatMap { townLifeRequest ->
                val idx = request.pathVariable("idx").toInt()
                townLifeService.updateTownLife(idx, townLifeRequest)
            }.switchIfEmpty(Mono.error(Exception("업데이트 실패")))
            .flatMap { Response(HttpStatus.OK,"업데이트 성공").toServerResponse() }
            .onErrorResume { it.toServerResponse() }

    fun delete(request: ServerRequest): Mono<ServerResponse> =
        Mono.justOrEmpty(request.pathVariable("idx").toInt())
            .switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.BAD_REQUEST, "잚못된 요청")))
            .flatMap(townLifeService::deleteTownLife)
            .flatMap { Response(HttpStatus.OK,"삭제 성공").toServerResponse() }
            .onErrorResume { it.toServerResponse() }
}