package kr.hs.dgsw.orange_market.domain.repository

import kr.hs.dgsw.orange_market.domain.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<UserEntity, String> {
    fun findByUserIdAndUserPw(userId: String?, userPw: String?): UserEntity?
    fun findByUserId(userId: String?): UserEntity?
    fun findByIdx(idx: Int?): UserEntity?
}