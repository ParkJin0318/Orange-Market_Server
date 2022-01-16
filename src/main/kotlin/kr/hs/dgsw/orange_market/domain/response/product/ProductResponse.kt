package kr.hs.dgsw.orange_market.domain.response.product

import java.util.*

data class ProductResponse(
    val idx: Int,
    val categoryIdx: Int,
    val category: String,
    val title: String,
    val contents: String,
    val price: String,
    val createAt: Date,
    val isSold: Boolean,
    val userIdx: Int,
    val name: String,
    val location: String,
    val profileImage: String,
    val city: String,
    val images: List<String>,
    val likeUsers: List<Int>
)