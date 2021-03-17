package kr.hs.dgsw.orange_market.domain.request

import kr.hs.dgsw.orange_market.domain.entity.UserEntity

class RegisterRequest {
    val userId: String? = null
    val userPw: String? = null
    val name: String? = null
    val city: String? = null
    val location: String? = null
    val profileImage: String? = null
}

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