package kr.hs.dgsw.orange_market.domain.mapper

import kr.hs.dgsw.orange_market.domain.entity.user.UserEntity
import kr.hs.dgsw.orange_market.domain.response.user.UserResponse

/**
 * Entity -> Response
 */
fun UserEntity.toResponse(): UserResponse {
    return UserResponse(
        this.idx!!,
        this.userId!!,
        this.name!!,
        this.city!!,
        this.location!!,
        this.profileImage!!
    )
}