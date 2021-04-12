package kr.hs.dgsw.orange_market.service.product

import kr.hs.dgsw.orange_market.domain.entity.product.ProductImageEntity
import kr.hs.dgsw.orange_market.domain.mapper.toEntity
import kr.hs.dgsw.orange_market.domain.mapper.toResponse
import kr.hs.dgsw.orange_market.domain.repository.product.ProductImageRepository
import kr.hs.dgsw.orange_market.domain.repository.product.ProductRepository
import kr.hs.dgsw.orange_market.domain.request.product.ProductRequest
import kr.hs.dgsw.orange_market.domain.response.product.ProductResponse
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import javax.transaction.Transactional

@Service
class ProductServiceImpl(
    private val productRepository: ProductRepository,
    private val productImageRepository: ProductImageRepository
): ProductService {

    override fun getAllProduct(city: String): Mono<List<ProductResponse>> =
        Mono.justOrEmpty(productRepository.findAllByCityEquals(city).orElse(emptyList()).map { productEntity ->
            val imageList = productImageRepository
                .findAllByProductIdxEquals(productEntity.idx!!)
                .orElse(emptyList())
                .map(ProductImageEntity::imageUrl)

            productEntity.toResponse(imageList)
        })

    override fun getProduct(idx: Int): Mono<ProductResponse> =
        Mono.justOrEmpty(productRepository.findByIdxEquals(idx).map { productEntity ->
            val imageList = productImageRepository
                .findAllByProductIdxEquals(idx)
                .orElse(emptyList())
                .map(ProductImageEntity::imageUrl)

            productEntity.toResponse(imageList)
        })


    override fun saveProduct(productRequest: ProductRequest): Mono<Int> =
        Mono.justOrEmpty(productRepository.save(productRequest.toEntity()).idx)

    override fun saveProductImage(productIdx: Int, imageList: List<String>): Mono<List<ProductImageEntity>> =
        Mono.justOrEmpty(imageList.map { image ->
            ProductImageEntity().apply {
                this.productIdx = productIdx
                this.imageUrl = image
            }
        }.map(productImageRepository::save))

    @Transactional
    override fun deleteProduct(idx: Int): Mono<Int> {
        val isDelete = productRepository.deleteByIdxEquals(idx)
        if (isDelete.get() == 0)
            return Mono.empty()

       return Mono.justOrEmpty(isDelete)
    }

}