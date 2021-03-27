package kr.hs.dgsw.orange_market.service.user

import kr.hs.dgsw.orange_market.domain.entity.UserEntity

interface UserService {
    fun getUser(idx: Int): UserEntity
}