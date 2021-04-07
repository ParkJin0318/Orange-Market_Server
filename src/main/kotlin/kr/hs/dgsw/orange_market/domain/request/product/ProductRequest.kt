package kr.hs.dgsw.orange_market.domain.request.product

class ProductRequest {
    var title: String? = null
    var contents: String? = null
    var price: String? = null
    var isSold: Int? = null
    var userIdx: Int? = null
    var city: String? = null
    var imageList: List<String>? = null
}