package kr.hs.dgsw.orange_market.domain.mapper

import kr.hs.dgsw.orange_market.domain.entity.product.ProductCategoryEntity
import kr.hs.dgsw.orange_market.domain.entity.product.ProductPostEntity
import kr.hs.dgsw.orange_market.domain.entity.user.UserEntity
import kr.hs.dgsw.orange_market.domain.request.product.ProductPostRequest
import kr.hs.dgsw.orange_market.domain.response.product.ProductResponse
import kr.hs.dgsw.orange_market.extension.toBoolean
import kr.hs.dgsw.orange_market.extension.toStringFormat
import java.util.*

/**
 * Request -> Entity
 */
fun ProductPostRequest.toEntity(): ProductPostEntity {
    return ProductPostEntity().apply {
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
fun ProductPostEntity.toResponse(
    user: UserEntity,
    category: ProductCategoryEntity,
    images: List<String>,
    likeUsers: List<Int>
): ProductResponse =
    ProductResponse(
        this.idx!!,
        this.categoryIdx!!,
        category.name!!,
        this.title!!,
        this.contents!!,
        this.price!!,
        this.createAt!!,
        this.isSold!!.toBoolean(),
        this.userIdx!!,
        user.name!!,
        user.location!!,
        user.profileImage ?: "",
        this.city!!,
        images,
        likeUsers
    )