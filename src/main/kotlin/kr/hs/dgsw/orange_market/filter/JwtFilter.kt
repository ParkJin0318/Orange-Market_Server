package kr.hs.dgsw.orange_market.filter

import kr.hs.dgsw.orange_market.extension.toServerResponse
import kr.hs.dgsw.orange_market.lib.AuthorizationExtractor
import kr.hs.dgsw.orange_market.service.jwt.JwtServiceImpl
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.HandlerFunction
import org.springframework.web.reactive.function.server.HandlerFilterFunction
import org.springframework.web.reactive.function.server.ServerRequest

@Component
class JwtFilter(
    private val jwtService: JwtServiceImpl
): HandlerFilterFunction<ServerResponse?, ServerResponse?> {

    override fun filter(serverRequest: ServerRequest, handlerFunction: HandlerFunction<ServerResponse?>): Mono<ServerResponse?> {
        val token: String = AuthorizationExtractor.extract(serverRequest, "Bearer")

        return jwtService.validateToken(token)
            .flatMap {
                serverRequest.attributes()["user"] = it
                handlerFunction.handle(serverRequest)
            }.onErrorResume { it.toServerResponse() }
    }
}