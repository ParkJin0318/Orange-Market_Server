package kr.hs.dgsw.orange_market.handler

import kr.hs.dgsw.orange_market.domain.entity.user.UserEntity
import kr.hs.dgsw.orange_market.domain.mapper.toResponse
import kr.hs.dgsw.orange_market.domain.request.user.LocationRequest
import kr.hs.dgsw.orange_market.domain.response.base.Response
import kr.hs.dgsw.orange_market.domain.response.base.ResponseData
import kr.hs.dgsw.orange_market.extension.toServerResponse
import kr.hs.dgsw.orange_market.service.user.UserServiceImpl
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class UserHandler(
    private val userService: UserServiceImpl
) {
    fun getUserProfile(request: ServerRequest): Mono<ServerResponse> =
        Mono.justOrEmpty(request.attribute("user"))
            .switchIfEmpty(Mono.error(Exception("Bad Request")))
            .cast(UserEntity::class.java)
            .flatMap {
                ResponseData("조회 성공", it.toResponse()).toServerResponse()
            }.onErrorResume {
                Response(it.message).toServerResponse()
            }

    fun getUser(request: ServerRequest): Mono<ServerResponse> =
        Mono.justOrEmpty(request.pathVariable("idx").toInt())
            .switchIfEmpty(Mono.error(Exception("Bad Request")))
            .flatMap(userService::getUser)
            .switchIfEmpty(Mono.error(Exception("사용자가 없음")))
            .flatMap {
                ResponseData("조회 성공", it).toServerResponse()
            }.onErrorResume {
                Response(it.message).toServerResponse()
            }

    fun updateLocation(request: ServerRequest): Mono<ServerResponse> =
        request.bodyToMono(LocationRequest::class.java)
            .switchIfEmpty(Mono.error(Exception("Bad Request")))
            .flatMap { locationRequest ->
                val user = request.attribute("user").get() as UserEntity
                userService.updateLocation(user, locationRequest)
            }.switchIfEmpty(Mono.error(Exception("위치 업데이트 실패")))
            .flatMap {
                Response("위치 업데이트 성공").toServerResponse()
            }.onErrorResume {
                Response(it.message).toServerResponse()
            }
}