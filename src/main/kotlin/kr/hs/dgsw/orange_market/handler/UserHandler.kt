package kr.hs.dgsw.orange_market.handler

import kr.hs.dgsw.orange_market.domain.entity.user.UserEntity
import kr.hs.dgsw.orange_market.domain.request.user.LocationRequest
import kr.hs.dgsw.orange_market.domain.response.base.Response
import kr.hs.dgsw.orange_market.domain.response.base.ResponseData
import kr.hs.dgsw.orange_market.extension.toServerResponse
import kr.hs.dgsw.orange_market.service.user.UserServiceImpl
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class UserHandler(
    private val userService: UserServiceImpl
) {
    fun getUser(request: ServerRequest): Mono<ServerResponse> =
        userService.getUser(request.pathVariable("idx").toInt())
            .flatMap {
                ResponseData("조회 성공", it).toServerResponse()
            }.onErrorResume {
                Response(it.message).toServerResponse()
            }

    fun updateLocation(request: ServerRequest): Mono<ServerResponse> =
        request.bodyToMono(LocationRequest::class.java).flatMap { locationRequest ->
            val userEntity: UserEntity = request.attribute("user").get() as UserEntity
            userEntity.city = locationRequest.city
            userEntity.location = locationRequest.location

            userService.updateLocation(userEntity)
        }.flatMap {
            if (it) Response("위치 업데이트 성공").toServerResponse()
            else Response("위치 업데이트 실패").toServerResponse()
        }.onErrorResume {
            Response(it.message).toServerResponse()
        }
}