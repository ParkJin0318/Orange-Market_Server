package kr.hs.dgsw.orange_market.service.jwt

import kr.hs.dgsw.orange_market.domain.response.user.UserResponse
import reactor.core.publisher.Mono

interface JwtService {
    fun createToken(idx: Int): Mono<String>
    fun validateToken(token: String): Mono<UserResponse>
}