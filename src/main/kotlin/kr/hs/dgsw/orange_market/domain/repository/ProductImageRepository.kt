package kr.hs.dgsw.orange_market.domain.repository

import kr.hs.dgsw.orange_market.domain.entity.ProductImageEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductImageRepository: JpaRepository<ProductImageEntity, Any> {
    fun findAllByProductIdxEquals(productIdx: Int): List<ProductImageEntity>
}