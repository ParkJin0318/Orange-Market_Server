package kr.hs.dgsw.orange_market.domain.mapper

import kr.hs.dgsw.orange_market.domain.entity.product.ProductEntity
import kr.hs.dgsw.orange_market.domain.request.product.ProductRequest
import kr.hs.dgsw.orange_market.domain.response.product.ProductResponse
import kr.hs.dgsw.orange_market.extension.toBoolean
import kr.hs.dgsw.orange_market.extension.toStringFormat
import java.util.*

/**
 * Request -> Entity
 */
fun ProductRequest.toEntity(): ProductEntity {
    return ProductEntity().apply {
        this.categoryIdx = this@toEntity.categoryIdx
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
fun ProductEntity.toResponse(images: List<String>, likeUsers: List<Int>): ProductResponse {
    return ProductResponse(
        this.idx!!,
        this.categoryIdx!!,
        this.title!!,
        this.contents!!,
        this.price!!,
        this.createAt!!,
        this.isSold!!.toBoolean(),
        this.userIdx!!,
        this.city!!,
        images,
        likeUsers
    )
}