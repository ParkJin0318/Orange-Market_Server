package kr.hs.dgsw.orange_market.service.user

import kr.hs.dgsw.orange_market.domain.entity.user.UserEntity
import kr.hs.dgsw.orange_market.domain.mapper.toResponse
import kr.hs.dgsw.orange_market.domain.repository.user.UserRepository
import kr.hs.dgsw.orange_market.domain.request.user.LocationRequest
import kr.hs.dgsw.orange_market.domain.response.user.UserResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class UserServiceImpl: UserService {

    @Autowired
    private lateinit var userRepository: UserRepository

    override fun getUser(idx: Int): Mono<UserResponse> =
        Mono.justOrEmpty(userRepository.findByIdx(idx).map {
            it.toResponse()
        })

    override fun updateLocation(userEntity: UserEntity): Mono<Boolean> =
        Mono.justOrEmpty(userRepository.save(userEntity).userId != null)
}