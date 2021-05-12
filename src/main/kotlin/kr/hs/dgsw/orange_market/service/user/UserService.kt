package kr.hs.dgsw.orange_market.service.user

import kr.hs.dgsw.orange_market.domain.entity.user.UserEntity
import kr.hs.dgsw.orange_market.domain.request.user.LocationRequest
import kr.hs.dgsw.orange_market.domain.request.user.UserRequest
import kr.hs.dgsw.orange_market.domain.response.user.UserResponse
import reactor.core.publisher.Mono

interface UserService {
    fun getUser(idx: Int): Mono<UserResponse>
    fun updateLocation(userEntity: UserEntity, locationRequest: LocationRequest): Mono<UserEntity>
    fun updateUser(userEntity: UserEntity, userRequest: UserRequest): Mono<UserEntity>
}