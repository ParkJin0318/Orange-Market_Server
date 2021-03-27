package kr.hs.dgsw.orange_market.service.jwt

import kr.hs.dgsw.orange_market.domain.entity.UserEntity

interface JwtService {
    fun createToken(idx: Int): String
    fun validateToken(token: String): UserEntity
}