package kr.hs.dgsw.orange_market.domain.response.product

data class ProductResponse(
    val idx: Int,
    val title: String,
    val contents: String,
    val price: String,
    val createAt: String,
    val isSold: Int,
    val city: String,
    val userIdx: Int,
    val imageList: List<String?>
)