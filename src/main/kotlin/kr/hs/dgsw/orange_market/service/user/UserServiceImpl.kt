package kr.hs.dgsw.orange_market.service.user

import kr.hs.dgsw.orange_market.domain.entity.user.UserEntity
import kr.hs.dgsw.orange_market.domain.mapper.toResponse
import kr.hs.dgsw.orange_market.domain.repository.user.UserRepository
import kr.hs.dgsw.orange_market.domain.request.user.LocationRequest
import kr.hs.dgsw.orange_market.domain.response.user.UserResponse
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class UserServiceImpl(
    private val userRepository: UserRepository
): UserService {

    override fun getUser(idx: Int): Mono<UserResponse> =
        Mono.justOrEmpty(
            userRepository.findByIdx(idx).map { it.toResponse() }
        ).switchIfEmpty(Mono.error(Exception("사용자가 없음")))

    override fun updateLocation(userEntity: UserEntity, locationRequest: LocationRequest): Mono<UserEntity> =
        Mono.justOrEmpty(
            userRepository.save(userEntity.apply {
                this.city = locationRequest.city
                this.location = locationRequest.location
            })
        ).switchIfEmpty(Mono.error(Exception("위치 업데이트 실패")))
}