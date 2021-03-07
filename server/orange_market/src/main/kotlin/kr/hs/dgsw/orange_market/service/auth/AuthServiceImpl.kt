package kr.hs.dgsw.orange_market.service.auth

import kr.hs.dgsw.orange_market.domain.entity.UserEntity
import kr.hs.dgsw.orange_market.domain.model.request.LoginRequest
import kr.hs.dgsw.orange_market.domain.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException

@Service
class AuthServiceImpl: AuthService {

    @Autowired
    private lateinit var userRepository: UserRepository

    override fun login(loginRequest: LoginRequest): String? {
        val user: UserEntity = userRepository.findByIdAndPassword(loginRequest.id, loginRequest.password)
            ?: throw HttpClientErrorException(HttpStatus.UNAUTHORIZED, "인증 실패.")
        return user.id
    }
}