package kr.hs.dgsw.orange_market.service.jwt

import kr.hs.dgsw.orange_market.domain.entity.user.UserEntity
import reactor.core.publisher.Mono

interface JwtService {
    fun createToken(idx: Int): Mono<String>
    fun validateToken(token: String): Mono<UserEntity>
}