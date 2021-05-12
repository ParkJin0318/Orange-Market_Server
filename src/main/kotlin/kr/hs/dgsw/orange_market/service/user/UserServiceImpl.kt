package kr.hs.dgsw.orange_market.service.user

import kr.hs.dgsw.orange_market.domain.entity.user.UserEntity
import kr.hs.dgsw.orange_market.domain.mapper.toResponse
import kr.hs.dgsw.orange_market.domain.repository.user.UserRepository
import kr.hs.dgsw.orange_market.domain.request.user.LocationRequest
import kr.hs.dgsw.orange_market.domain.request.user.UserRequest
import kr.hs.dgsw.orange_market.domain.response.user.UserResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import reactor.core.publisher.Mono

@Service
class UserServiceImpl(
    private val userRepository: UserRepository
): UserService {

    override fun getUser(idx: Int): Mono<UserResponse> =
        Mono.justOrEmpty(
            userRepository.findByIdx(idx)?.toResponse()
        ).switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.NOT_FOUND, "사용자 없음")))

    override fun updateLocation(userEntity: UserEntity, locationRequest: LocationRequest): Mono<UserEntity> =
        Mono.justOrEmpty(
            userRepository.save(userEntity.apply {
                this.city = locationRequest.city
                this.location = locationRequest.location
            })
        ).switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.UNPROCESSABLE_ENTITY, "위치 업데이트 실패")))

    override fun updateUser(userEntity: UserEntity, userRequest: UserRequest): Mono<UserEntity> =
        Mono.justOrEmpty(
            userRepository.save(userEntity.apply {
                this.name = userRequest.name
                this.profileImage = userRequest.profileImage
            })
        ).switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.UNPROCESSABLE_ENTITY, "정보 업데이트 실패")))
}