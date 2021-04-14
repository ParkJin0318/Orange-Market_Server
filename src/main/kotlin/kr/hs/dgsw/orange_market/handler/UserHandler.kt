package kr.hs.dgsw.orange_market.handler

import kr.hs.dgsw.orange_market.domain.entity.user.UserEntity
import kr.hs.dgsw.orange_market.domain.mapper.toResponse
import kr.hs.dgsw.orange_market.domain.request.user.LocationRequest
import kr.hs.dgsw.orange_market.domain.response.base.Response
import kr.hs.dgsw.orange_market.domain.response.base.ResponseData
import kr.hs.dgsw.orange_market.extension.toServerResponse
import kr.hs.dgsw.orange_market.service.user.UserServiceImpl
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class UserHandler(
    private val userService: UserServiceImpl
) {
    fun getUserProfile(request: ServerRequest): Mono<ServerResponse> =
        Mono.justOrEmpty(request.attribute("user"))
            .switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.BAD_REQUEST, "잚못된 요청")))
            .cast(UserEntity::class.java)
            .flatMap { ResponseData(HttpStatus.OK, "조회 성공", it.toResponse()).toServerResponse() }
            .onErrorResume { it.toServerResponse() }

    fun getUser(request: ServerRequest): Mono<ServerResponse> =
        Mono.justOrEmpty(request.pathVariable("idx").toInt())
            .switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.BAD_REQUEST, "잚못된 요청")))
            .flatMap(userService::getUser)
            .flatMap { ResponseData(HttpStatus.OK,"조회 성공", it).toServerResponse() }
            .onErrorResume { it.toServerResponse() }

    fun updateLocation(request: ServerRequest): Mono<ServerResponse> =
        request.bodyToMono(LocationRequest::class.java)
            .switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.BAD_REQUEST, "잚못된 요청")))
            .flatMap { locationRequest ->
                val user = request.attribute("user").get() as UserEntity
                userService.updateLocation(user, locationRequest)
            }.flatMap { Response(HttpStatus.OK,"위치 업데이트 성공").toServerResponse() }
            .onErrorResume { it.toServerResponse() }
}