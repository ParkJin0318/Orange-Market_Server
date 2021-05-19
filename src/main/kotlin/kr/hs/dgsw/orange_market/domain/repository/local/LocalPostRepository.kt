package kr.hs.dgsw.orange_market.domain.repository.local

import kr.hs.dgsw.orange_market.domain.entity.local.LocalPostEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LocalPostRepository: JpaRepository<LocalPostEntity, Any> {
    fun findAllByCityEquals(city: String): List<LocalPostEntity>
    fun findByIdxEquals(idx: Int): LocalPostEntity?
    fun deleteByIdxEquals(idx: Int): Int?
}