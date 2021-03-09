package kr.hs.dgsw.orange_market.domain.repository

import kr.hs.dgsw.orange_market.domain.entity.ProductEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository: JpaRepository<ProductEntity, Any>