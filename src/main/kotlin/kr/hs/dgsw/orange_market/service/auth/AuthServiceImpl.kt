package kr.hs.dgsw.orange_market.service.auth

import kr.hs.dgsw.orange_market.domain.request.auth.LoginRequest
import kr.hs.dgsw.orange_market.domain.repository.user.UserRepository
import kr.hs.dgsw.orange_market.domain.request.auth.RegisterRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class AuthServiceImpl: AuthService {

    @Autowired
    private lateinit var userRepository: UserRepository

    override fun login(loginRequest: LoginRequest): Mono<Int> =
        Mono.justOrEmpty(userRepository.findByUserIdAndUserPw(
            loginRequest.userId!!,
            loginRequest.userPw!!
        ).map { it.idx })

    override fun register(registerRequest: RegisterRequest): Mono<Boolean> =
        Mono.justOrEmpty(userRepository.findByUserId(registerRequest.userId!!)
            .filter { it != null }
            .map {
                userRepository.save(it)
            } != null)
}