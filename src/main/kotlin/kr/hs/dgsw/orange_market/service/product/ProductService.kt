package kr.hs.dgsw.orange_market.service.product

import kr.hs.dgsw.orange_market.domain.entity.product.ProductCategoryEntity
import kr.hs.dgsw.orange_market.domain.request.product.ProductPostRequest
import kr.hs.dgsw.orange_market.domain.response.product.ProductResponse
import reactor.core.publisher.Mono

interface ProductService {
    fun getAllProduct(city: String): Mono<List<ProductResponse>>

    fun getProduct(idx: Int): Mono<ProductResponse>

    fun getAllCategory(): Mono<List<ProductCategoryEntity>>

    fun getAllLikeProduct(userIdx: Int): Mono<List<ProductResponse>>

    fun saveProduct(productPostRequest: ProductPostRequest): Mono<Unit>

    fun saveProductImage(imageList: List<String>?, idx: Int?): Mono<Unit>

    fun likeProduct(productIdx: Int, userIdx: Int): Mono<Unit>

    fun updateProduct(idx: Int, productPostRequest: ProductPostRequest): Mono<Unit>

    fun updateSold(idx: Int): Mono<Unit>

    fun deleteProduct(idx: Int): Mono<Unit>
}