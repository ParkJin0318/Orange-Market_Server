package kr.hs.dgsw.orange_market.service.auth

import kr.hs.dgsw.orange_market.domain.entity.user.UserEntity
import kr.hs.dgsw.orange_market.domain.request.auth.LoginRequest
import kr.hs.dgsw.orange_market.domain.request.auth.RegisterRequest
import reactor.core.publisher.Mono

interface AuthService {
    fun login(loginRequest: LoginRequest): Mono<Int>
    fun register(registerRequest: RegisterRequest): Mono<UserEntity>
}