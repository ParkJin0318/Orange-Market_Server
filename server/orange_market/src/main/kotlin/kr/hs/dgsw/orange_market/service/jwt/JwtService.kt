package kr.hs.dgsw.orange_market.service.jwt

import kr.hs.dgsw.orange_market.domain.entity.UserEntity

interface JwtService {
    fun createToken(userId: String): String
    fun validateToken(token: String): UserEntity
}