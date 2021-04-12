package kr.hs.dgsw.orange_market.service.product

import kr.hs.dgsw.orange_market.domain.entity.product.ProductImageEntity
import kr.hs.dgsw.orange_market.domain.request.product.ProductRequest
import kr.hs.dgsw.orange_market.domain.response.product.ProductResponse
import reactor.core.publisher.Mono

interface ProductService {
    fun getAllProduct(city: String): Mono<List<ProductResponse>>
    fun getProduct(idx: Int): Mono<ProductResponse>
    fun saveProduct(productRequest: ProductRequest): Mono<Int>
    fun saveProductImage(productIdx: Int, imageList: List<String>): Mono<List<ProductImageEntity>>
}