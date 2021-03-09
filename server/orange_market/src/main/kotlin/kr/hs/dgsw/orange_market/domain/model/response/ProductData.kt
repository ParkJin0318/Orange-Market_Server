package kr.hs.dgsw.orange_market.domain.model.response

import java.util.*

data class ProductData(
    val title: String,
    val contents: String,
    val price: String,
    val createAt: Date?,
    val isSold: Int,
    val userId: String,
    val city: String,
    val location: String,
    val imageList: List<String?>
)