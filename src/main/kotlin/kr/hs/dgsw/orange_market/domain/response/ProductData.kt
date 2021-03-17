package kr.hs.dgsw.orange_market.domain.response

import kr.hs.dgsw.orange_market.domain.entity.ProductEntity
import java.sql.Timestamp

data class ProductData(
    val title: String,
    val contents: String,
    val price: String,
    val createAt: Timestamp,
    val isSold: Int,
    val userId: String,
    val city: String,
    val location: String,
    val imageList: List<String?>
)

fun ProductEntity.toModel(imageList: List<String?>): ProductData {
    return ProductData(
        this.title!!,
        this.contents!!,
        this.price!!,
        this.createAt!!,
        this.isSold!!,
        this.userId!!,
        this.city!!,
        this.location!!,
        imageList
    )
}