package kr.hs.dgsw.orange_market.domain.mapper

import kr.hs.dgsw.orange_market.domain.entity.product.ProductEntity
import kr.hs.dgsw.orange_market.domain.request.product.ProductRequest
import kr.hs.dgsw.orange_market.domain.response.product.ProductResponse
import kr.hs.dgsw.orange_market.extension.toStringFormat
import java.util.*

/**
 * Request -> Entity
 */
fun ProductRequest.toEntity(): ProductEntity {
    return ProductEntity().apply {
        this.title = this@toEntity.title
        this.contents = this@toEntity.contents
        this.price = this@toEntity.price
        this.createAt = Date().toStringFormat()
        this.isSold = this@toEntity.isSold
        this.userIdx = this@toEntity.userIdx
        this.city = this@toEntity.city
    }
}

/**
 * Entity -> Response
 */
fun ProductEntity.toResponse(imageList: List<String?>): ProductResponse {
    return ProductResponse(
        this.idx!!,
        this.title!!,
        this.contents!!,
        this.price!!,
        this.createAt!!,
        this.isSold!!,
        this.city!!,
        this.userIdx!!,
        imageList
    )
}