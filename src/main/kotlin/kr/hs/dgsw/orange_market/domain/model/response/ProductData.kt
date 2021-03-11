package kr.hs.dgsw.orange_market.domain.model.response

import java.time.LocalDateTime

data class ProductData(
    val title: String,
    val contents: String,
    val price: String,
    val createAt: LocalDateTime?,
    val isSold: Int,
    val userId: String,
    val city: String,
    val location: String,
    val imageList: List<String?>
)