package kr.hs.dgsw.orange_market.handler

import kr.hs.dgsw.orange_market.domain.request.local.LocalCommentRequest
import kr.hs.dgsw.orange_market.domain.request.local.LocalPostRequest
import kr.hs.dgsw.orange_market.domain.response.base.Response
import kr.hs.dgsw.orange_market.domain.response.base.ResponseData
import kr.hs.dgsw.orange_market.extension.toServerResponse
import kr.hs.dgsw.orange_market.service.local.LocalServiceImpl
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class LocalHandler(
    private val localService: LocalServiceImpl
) {
    fun getAllPost(request: ServerRequest): Mono<ServerResponse> =
        Mono.justOrEmpty(request.queryParam("city"))
            .switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.BAD_REQUEST, "잚못된 요청")))
            .flatMap(localService::getAllLocalPost)
            .flatMap { ResponseData(HttpStatus.OK,"조회 성공", it).toServerResponse() }
            .onErrorResume { it.toServerResponse() }

    fun getPost(request: ServerRequest): Mono<ServerResponse> =
        Mono.justOrEmpty(request.pathVariable("idx").toInt())
            .switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.BAD_REQUEST, "잚못된 요청")))
            .flatMap(localService::getLocalPost)
            .flatMap { ResponseData(HttpStatus.OK,"조회 성공", it).toServerResponse() }
            .onErrorResume { it.toServerResponse() }

    fun getAllComment(request: ServerRequest): Mono<ServerResponse> =
        Mono.justOrEmpty(request.pathVariable("idx").toInt())
            .switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.BAD_REQUEST, "잚못된 요청")))
            .flatMap(localService::getAllLocalComment)
            .flatMap { ResponseData(HttpStatus.OK,"조회 성공", it).toServerResponse() }
            .onErrorResume { it.toServerResponse() }

    fun getAllTopic(request: ServerRequest): Mono<ServerResponse> =
        localService.getAllTopic()
            .flatMap { ResponseData(HttpStatus.OK,"조회 성공", it).toServerResponse() }
            .onErrorResume { it.toServerResponse() }

    fun savePost(request: ServerRequest): Mono<ServerResponse> =
        request.bodyToMono(LocalPostRequest::class.java)
            .switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.BAD_REQUEST, "잚못된 요청")))
            .flatMap(localService::saveLocalPost)
            .switchIfEmpty(Mono.error(Exception("등록 실패")))
            .flatMap { Response(HttpStatus.OK,"등록 성공").toServerResponse() }
            .onErrorResume { it.toServerResponse() }

    fun saveComment(request: ServerRequest): Mono<ServerResponse> =
        request.bodyToMono(LocalCommentRequest::class.java)
            .switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.BAD_REQUEST, "잚못된 요청")))
            .flatMap(localService::saveLocalComment)
            .switchIfEmpty(Mono.error(Exception("등록 실패")))
            .flatMap { Response(HttpStatus.OK,"등록 성공").toServerResponse() }
            .onErrorResume { it.toServerResponse() }

    fun updatePost(request: ServerRequest): Mono<ServerResponse> =
        request.bodyToMono(LocalPostRequest::class.java)
            .switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.BAD_REQUEST, "잚못된 요청")))
            .flatMap { townLifeRequest ->
                val idx = request.pathVariable("idx").toInt()
                localService.updateLocalPost(idx, townLifeRequest)
            }.switchIfEmpty(Mono.error(Exception("업데이트 실패")))
            .flatMap { Response(HttpStatus.OK,"업데이트 성공").toServerResponse() }
            .onErrorResume { it.toServerResponse() }

    fun updateComment(request: ServerRequest): Mono<ServerResponse> =
        request.bodyToMono(LocalCommentRequest::class.java)
            .switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.BAD_REQUEST, "잚못된 요청")))
            .flatMap { townLifeRequest ->
                val idx = request.pathVariable("idx").toInt()
                localService.updateLocalComment(idx, townLifeRequest)
            }.switchIfEmpty(Mono.error(Exception("업데이트 실패")))
            .flatMap { Response(HttpStatus.OK,"업데이트 성공").toServerResponse() }
            .onErrorResume { it.toServerResponse() }

    fun deletePost(request: ServerRequest): Mono<ServerResponse> =
        Mono.justOrEmpty(request.pathVariable("idx").toInt())
            .switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.BAD_REQUEST, "잚못된 요청")))
            .flatMap(localService::deleteLocalPost)
            .flatMap { Response(HttpStatus.OK,"삭제 성공").toServerResponse() }
            .onErrorResume { it.toServerResponse() }

    fun deleteComment(request: ServerRequest): Mono<ServerResponse> =
        Mono.justOrEmpty(request.pathVariable("idx").toInt())
            .switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.BAD_REQUEST, "잚못된 요청")))
            .flatMap(localService::deleteLocalComment)
            .flatMap { Response(HttpStatus.OK,"삭제 성공").toServerResponse() }
            .onErrorResume { it.toServerResponse() }
}