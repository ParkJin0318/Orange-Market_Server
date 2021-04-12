package kr.hs.dgsw.orange_market.domain.repository.product

import kr.hs.dgsw.orange_market.domain.entity.product.ProductEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ProductRepository: JpaRepository<ProductEntity, Any> {
    fun findAllByCityEquals(city: String): Optional<List<ProductEntity>>
    fun findByIdxEquals(idx: Int): Optional<ProductEntity>
    fun deleteByIdxEquals(idx: Int): Optional<Int>
}