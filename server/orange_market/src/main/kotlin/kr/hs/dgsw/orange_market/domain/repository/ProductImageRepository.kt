package kr.hs.dgsw.orange_market.domain.repository

import kr.hs.dgsw.orange_market.domain.entity.ProductImageEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ProductImageRepository: JpaRepository<ProductImageEntity, Any> {
    fun findAllByProductIdxEquals(productIdx: Int): List<ProductImageEntity>
}