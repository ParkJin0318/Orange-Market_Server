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

    @Transactional
    override fun getAllProduct(city: String): Mono<List<ProductResponse>> =
        Mono.justOrEmpty(productRepository.findAllByCityEquals(city).map { productEntity ->
            val imageList = productImageRepository
                .findAllByProductIdxEquals(productEntity?.idx!!)
                .map { it?.imageUrl }

            productEntity.toResponse(imageList)
        }).switchIfEmpty(Mono.error(Exception("조회 실패")))

    @Transactional
    override fun getProduct(idx: Int): Mono<ProductResponse> =
        Mono.justOrEmpty(
            productRepository.findByIdxEquals(idx)?.toResponse(
                productImageRepository.findAllByProductIdxEquals(idx).map { it?.imageUrl }
            )
        ).switchIfEmpty(Mono.error(Exception("조회 실패")))

    @Transactional
    override fun saveProduct(productRequest: ProductRequest): Mono<Unit> =
        Mono.justOrEmpty(productRepository.save(productRequest.toEntity()))
            .flatMap { saveProductImage(productRequest.imageList, it.idx) }

    @Transactional
    override fun saveProductImage(imageList: List<String>?, idx: Int?): Mono<Unit> =
        Mono.justOrEmpty(
            imageList?.map { image ->
                ProductImageEntity().apply {
                    this.productIdx = idx
                    this.imageUrl = image
                }
            }?.map(productImageRepository::save))
            .switchIfEmpty(Mono.error(Exception("저장 실패")))
            .flatMap { Mono.just(Unit) }

    @Transactional
    override fun updateProduct(idx: Int, productRequest: ProductRequest): Mono<Unit> =
        Mono.justOrEmpty(productRepository.save(productRequest.toEntity().apply { this.idx = idx }))
            .switchIfEmpty(Mono.error(Exception("저장 실패")))
            .flatMap { saveProductImage(productRequest.imageList, it.idx) }

    @Transactional
    override fun deleteProduct(idx: Int): Mono<Unit> =
        Mono.justOrEmpty(productRepository.deleteByIdxEquals(idx))
            .flatMap {
                if (it == 0) Mono.empty()
                else Mono.just(Unit)
            }.switchIfEmpty(Mono.error(Exception("삭제 실패")))
}