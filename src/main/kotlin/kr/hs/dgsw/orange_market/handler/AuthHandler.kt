package kr.hs.dgsw.orange_market.handler

import kr.hs.dgsw.orange_market.domain.request.auth.LoginRequest
import kr.hs.dgsw.orange_market.domain.request.auth.RegisterRequest
import kr.hs.dgsw.orange_market.domain.response.base.Response
import kr.hs.dgsw.orange_market.domain.response.base.ResponseData
import kr.hs.dgsw.orange_market.extension.toServerResponse
import kr.hs.dgsw.orange_market.service.auth.AuthServiceImpl
import kr.hs.dgsw.orange_market.service.jwt.JwtServiceImpl
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpClientErrorException
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
            .switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.BAD_REQUEST, "잚못된 요청")))
            .flatMap(authService::login)
            .flatMap { jwtService.createToken(it.idx!!) }
            .flatMap { ResponseData(HttpStatus.OK, "로그인 성공", it).toServerResponse() }
            .onErrorResume { it.toServerResponse() }

    fun register(request: ServerRequest): Mono<ServerResponse> =
        request.bodyToMono(RegisterRequest::class.java)
            .switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.BAD_REQUEST, "잚못된 요청")))
            .flatMap(authService::register)
            .flatMap { Response(HttpStatus.OK, "회원가입 성공").toServerResponse() }
            .onErrorResume { it.toServerResponse() }
}