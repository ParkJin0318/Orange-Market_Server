package kr.hs.dgsw.orange_market.service.user

import kr.hs.dgsw.orange_market.domain.entity.user.UserEntity
import kr.hs.dgsw.orange_market.domain.request.user.LocationInfoRequest
import kr.hs.dgsw.orange_market.domain.request.user.UserInfoRequest
import kr.hs.dgsw.orange_market.domain.response.user.UserResponse
import reactor.core.publisher.Mono

interface UserService {
    fun getUser(idx: Int): Mono<UserResponse>
    fun updateLocation(userEntity: UserEntity, locationInfoRequest: LocationInfoRequest): Mono<UserEntity>
    fun updateUser(userEntity: UserEntity, userInfoRequest: UserInfoRequest): Mono<UserEntity>
}