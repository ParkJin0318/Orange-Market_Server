package kr.hs.dgsw.orange_market.service.product

import kr.hs.dgsw.orange_market.domain.request.ProductRequest
import kr.hs.dgsw.orange_market.domain.response.ProductData

interface ProductService {
    fun getAllProduct(city: String): List<ProductData>
    fun getProduct(idx: Int): ProductData
    fun saveProduct(productRequest: ProductRequest): Int
    fun saveProductImage(idx: Int, imageList: List<String>)
}