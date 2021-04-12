package kr.hs.dgsw.orange_market.service.auth

import kr.hs.dgsw.orange_market.domain.entity.user.UserEntity
import kr.hs.dgsw.orange_market.domain.mapper.toEntity
import kr.hs.dgsw.orange_market.domain.request.auth.LoginRequest
import kr.hs.dgsw.orange_market.domain.repository.user.UserRepository
import kr.hs.dgsw.orange_market.domain.request.auth.RegisterRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class AuthServiceImpl(
    private val userRepository: UserRepository
): AuthService {

    override fun login(loginRequest: LoginRequest): Mono<Int> =
        Mono.justOrEmpty(userRepository.findByUserIdAndUserPw(
            loginRequest.userId!!,
            loginRequest.userPw!!
        ).map(UserEntity::idx))

    override fun register(registerRequest: RegisterRequest): Mono<UserEntity> =
        Mono.justOrEmpty(userRepository.findByUserId(registerRequest.userId!!))
            .switchIfEmpty(Mono.justOrEmpty(userRepository.save(registerRequest.toEntity())))
            .flatMap { Mono.empty() }
}