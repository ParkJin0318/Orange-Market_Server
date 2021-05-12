package kr.hs.dgsw.orange_market.domain.response.product

data class ProductResponse(
    val idx: Int,
    val categoryIdx: Int,
    val title: String,
    val contents: String,
    val price: String,
    val createAt: String,
    val isSold: Boolean,
    val userIdx: Int,
    val city: String,
    val images: List<String>,
    val likeUsers: List<Int>
)