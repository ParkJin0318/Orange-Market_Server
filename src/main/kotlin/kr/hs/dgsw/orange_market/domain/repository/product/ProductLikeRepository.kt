package kr.hs.dgsw.orange_market.domain.repository.product

import kr.hs.dgsw.orange_market.domain.entity.product.ProductLikeEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ProductLikeRepository: JpaRepository<ProductLikeEntity, Any> {
    fun findAllByProductIdxEquals(productIdx: Int): List<ProductLikeEntity>
    fun findAllByUserIdxEquals(userIdx: Int): List<ProductLikeEntity>
    fun findByProductIdxAndUserIdx(productIdx: Int, userIdx: Int): ProductLikeEntity?
}