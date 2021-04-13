package kr.hs.dgsw.orange_market.service.product

import kr.hs.dgsw.orange_market.domain.request.product.ProductRequest
import kr.hs.dgsw.orange_market.domain.response.product.ProductResponse
import reactor.core.publisher.Mono

interface ProductService {
    fun getAllProduct(city: String): Mono<List<ProductResponse>>
    fun getProduct(idx: Int): Mono<ProductResponse>
    fun saveProduct(productRequest: ProductRequest): Mono<Unit>
    fun saveProductImage(imageList: List<String>?, idx: Int?): Mono<Unit>
    fun updateProduct(idx: Int, productRequest: ProductRequest): Mono<Unit>
    fun deleteProduct(idx: Int): Mono<Unit>
}