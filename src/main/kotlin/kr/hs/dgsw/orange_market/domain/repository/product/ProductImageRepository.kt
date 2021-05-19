package kr.hs.dgsw.orange_market.domain.repository.product

import kr.hs.dgsw.orange_market.domain.entity.product.ProductImageEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ProductImageRepository: JpaRepository<ProductImageEntity, Any> {
    fun findAllByPostIdxEquals(productIdx: Int): List<ProductImageEntity>
    fun deleteAllByPostIdxEquals(productIdx: Int): Int?
}