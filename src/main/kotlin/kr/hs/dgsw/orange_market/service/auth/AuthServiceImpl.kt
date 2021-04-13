package kr.hs.dgsw.orange_market.service.auth

import kr.hs.dgsw.orange_market.domain.entity.user.UserEntity
import kr.hs.dgsw.orange_market.domain.mapper.toEntity
import kr.hs.dgsw.orange_market.domain.request.auth.LoginRequest
import kr.hs.dgsw.orange_market.domain.repository.user.UserRepository
import kr.hs.dgsw.orange_market.domain.request.auth.RegisterRequest
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class AuthServiceImpl(
    private val userRepository: UserRepository
): AuthService {

    override fun login(loginRequest: LoginRequest): Mono<UserEntity> =
        Mono.justOrEmpty(userRepository.findByUserIdAndUserPw(
            loginRequest.userId!!,
            loginRequest.userPw!!
        )).switchIfEmpty(Mono.error(Exception("아이디 또는 비밀번호가 틀립니다.")))

    override fun register(registerRequest: RegisterRequest): Mono<UserEntity> =
        Mono.justOrEmpty(userRepository.findByUserId(registerRequest.userId!!))
            .switchIfEmpty(
                Mono.justOrEmpty(userRepository.save(registerRequest.toEntity()))
                    .switchIfEmpty(Mono.error(Exception("회원가입 실패")))
            ).flatMap { Mono.error(Exception("아이디 존재")) }
}