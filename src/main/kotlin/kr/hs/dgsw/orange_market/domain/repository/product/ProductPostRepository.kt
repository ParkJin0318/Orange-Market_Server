package kr.hs.dgsw.orange_market.domain.repository.product

import kr.hs.dgsw.orange_market.domain.entity.product.ProductPostEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductPostRepository: JpaRepository<ProductPostEntity, Any> {
    fun findAllByCityEquals(city: String): List<ProductPostEntity>
    fun findByIdxEquals(idx: Int): ProductPostEntity?
    fun deleteByIdxEquals(idx: Int): Int?
}