package kr.hs.dgsw.orange_market.service.product

import kr.hs.dgsw.orange_market.domain.model.request.ProductRequest
import kr.hs.dgsw.orange_market.domain.model.response.ProductData

interface ProductService {
    fun getAllProduct(): List<ProductData>
    fun saveProduct(productRequest: ProductRequest): Int
    fun saveProductImage(idx: Int, imageList: List<String>)
}