package kr.hs.dgsw.orange_market.domain.model.request

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
        userId = this@toEntity.userId
        userPw = this@toEntity.userPw
        name = this@toEntity.name
        city = this@toEntity.city
        location = this@toEntity.location
        profileImage = this@toEntity.profileImage
    }
}