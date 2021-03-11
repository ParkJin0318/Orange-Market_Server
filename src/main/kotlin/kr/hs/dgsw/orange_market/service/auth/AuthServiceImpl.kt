package kr.hs.dgsw.orange_market.service.auth

import kr.hs.dgsw.orange_market.domain.entity.UserEntity
import kr.hs.dgsw.orange_market.domain.model.request.LoginRequest
import kr.hs.dgsw.orange_market.domain.model.request.RegisterRequest
import kr.hs.dgsw.orange_market.domain.model.request.toEntity
import kr.hs.dgsw.orange_market.domain.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException

@Service
class AuthServiceImpl: AuthService {

    @Autowired
    private lateinit var userRepository: UserRepository

    override fun login(loginRequest: LoginRequest): Int? {
        val user: UserEntity = userRepository.findByUserIdAndUserPw(
            loginRequest.userId,
            loginRequest.userPw
        ) ?: throw HttpClientErrorException(HttpStatus.UNAUTHORIZED, "인증 실패")
        return user.idx
    }

    override fun register(registerRequest: RegisterRequest) {
        val user: UserEntity? = userRepository.findByUserId(registerRequest.userId)
        if (user == null) {
            userRepository.save(registerRequest.toEntity())
        } else {
            throw HttpClientErrorException(HttpStatus.UNAUTHORIZED, "이미 존재하는 아이디")
        }
    }
}