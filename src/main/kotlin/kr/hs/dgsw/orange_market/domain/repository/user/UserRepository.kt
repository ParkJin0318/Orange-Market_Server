package kr.hs.dgsw.orange_market.domain.repository.user

import kr.hs.dgsw.orange_market.domain.entity.user.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository: JpaRepository<UserEntity, Any> {
    fun findByUserIdAndUserPw(userId: String, userPw: String): Optional<UserEntity>
    fun findByUserId(userId: String): Optional<UserEntity>
    fun findByIdx(idx: Int): Optional<UserEntity>
}