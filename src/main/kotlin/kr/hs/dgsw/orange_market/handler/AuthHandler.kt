package kr.hs.dgsw.orange_market.handler

import kr.hs.dgsw.orange_market.domain.request.auth.LoginRequest
import kr.hs.dgsw.orange_market.domain.request.auth.RegisterRequest
import kr.hs.dgsw.orange_market.domain.response.base.Response
import kr.hs.dgsw.orange_market.domain.response.base.ResponseData
import kr.hs.dgsw.orange_market.extension.toServerResponse
import kr.hs.dgsw.orange_market.service.auth.AuthServiceImpl
import kr.hs.dgsw.orange_market.service.jwt.JwtServiceImpl
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class AuthHandler(
    private val authService: AuthServiceImpl,
    private val jwtService: JwtServiceImpl
) {
    fun login(request: ServerRequest): Mono<ServerResponse> =
        request.bodyToMono(LoginRequest::class.java)
            .switchIfEmpty(Mono.error(Exception("Bad Request")))
            .flatMap(authService::login)
            .switchIfEmpty(Mono.error(Exception("로그인 실패")))
            .flatMap(jwtService::createToken)
            .switchIfEmpty(Mono.error(Exception("토큰 오류")))
            .flatMap {
                ResponseData("로그인 성공", it).toServerResponse()
            }.onErrorResume {
                Response(it.message).toServerResponse()
            }

    fun register(request: ServerRequest): Mono<ServerResponse> =
        request.bodyToMono(RegisterRequest::class.java)
            .switchIfEmpty(Mono.error(Exception("Bad Request")))
            .flatMap(authService::register)
            .switchIfEmpty(Mono.error(Exception("회원가입 실패")))
            .flatMap {
                Response("회원가입 성공").toServerResponse()
            }.onErrorResume {
                Response(it.message).toServerResponse()
            }
}