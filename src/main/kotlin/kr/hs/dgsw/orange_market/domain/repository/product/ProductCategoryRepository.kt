package kr.hs.dgsw.orange_market.domain.repository.product

import kr.hs.dgsw.orange_market.domain.entity.product.ProductCategoryEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductCategoryRepository: JpaRepository<ProductCategoryEntity, Any> {
    fun findByIdxEquals(idx: Int): ProductCategoryEntity?
}