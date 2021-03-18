package kr.hs.dgsw.orange_market.domain.request

import kr.hs.dgsw.orange_market.domain.entity.ProductEntity
import java.sql.Timestamp
import java.util.*

class ProductRequest {
    var title: String? = null
    var contents: String? = null
    var price: String? = null
    var isSold: Int? = null
    var userIdx: Int? = null
    var city: String? = null
    var location: String? = null
    var imageList: List<String>? = null
}

fun ProductRequest.toEntity(): ProductEntity {
    return ProductEntity().apply {
        this.title = this@toEntity.title
        this.contents = this@toEntity.contents
        this.price = this@toEntity.price
        this.createAt = Timestamp(Date().time)
        this.isSold = this@toEntity.isSold
        this.userIdx = this@toEntity.userIdx
        this.city = this@toEntity.city
    }
}