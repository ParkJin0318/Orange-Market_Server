package kr.hs.dgsw.orange_market.domain.model.request

import kr.hs.dgsw.orange_market.domain.entity.ProductEntity
import java.util.*

class ProductRequest {
    var title: String? = null
    var contents: String? = null
    var price: String? = null
    var createAt: Date? = null
    var isSold: Int? = null
    var userId: String? = null
    var city: String? = null
    var location: String? = null
    var imageList: List<String>? = null
}

fun ProductRequest.toEntity(): ProductEntity {
    return ProductEntity().apply {
        this.title = this@toEntity.title
        this.contents = this@toEntity.contents
        this.price = this@toEntity.price
        this.createAt = this@toEntity.createAt
        this.isSold = this@toEntity.isSold
        this.userId = this@toEntity.userId
        this.city = this@toEntity.city
        this.location = this@toEntity.location
    }
}