package kr.hs.dgsw.orange_market.domain.model.request

import kr.hs.dgsw.orange_market.domain.entity.UserEntity

class RegisterRequest {
    val userId: String? = null
    val userPw: String? = null
    val name: String? = null
    val profileImage: String? = null
}

fun RegisterRequest.toEntity(): UserEntity {
    val user = UserEntity()
    user.userId = this.userId
    user.userPw = this.userPw
    user.name = this.name
    user.profileImage = this.profileImage
    return user
}