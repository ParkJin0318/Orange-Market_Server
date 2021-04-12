package kr.hs.dgsw.orange_market.domain.mapper

import kr.hs.dgsw.orange_market.domain.entity.user.UserEntity
import kr.hs.dgsw.orange_market.domain.request.auth.RegisterRequest

/**
 * Request -> Entity
 */
fun RegisterRequest.toEntity(): UserEntity {
    return UserEntity().apply {
        this.userId = this@toEntity.userId
        this.userPw = this@toEntity.userPw
        this.name = this@toEntity.name
        this.city = this@toEntity.city
        this.location = this@toEntity.location
        this.profileImage = this@toEntity.profileImage
    }
}