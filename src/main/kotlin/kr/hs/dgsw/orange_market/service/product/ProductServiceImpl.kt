package kr.hs.dgsw.orange_market.service.product

import kr.hs.dgsw.orange_market.domain.entity.product.CategoryEntity
import kr.hs.dgsw.orange_market.domain.entity.product.ProductImageEntity
import kr.hs.dgsw.orange_market.domain.entity.product.ProductLikeEntity
import kr.hs.dgsw.orange_market.domain.mapper.toEntity
import kr.hs.dgsw.orange_market.domain.mapper.toResponse
import kr.hs.dgsw.orange_market.domain.repository.product.CategoryRepository
import kr.hs.dgsw.orange_market.domain.repository.product.ProductImageRepository
import kr.hs.dgsw.orange_market.domain.repository.product.ProductLikeRepository
import kr.hs.dgsw.orange_market.domain.repository.product.ProductRepository
import kr.hs.dgsw.orange_market.domain.request.product.ProductRequest
import kr.hs.dgsw.orange_market.domain.response.product.ProductResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import reactor.core.publisher.Mono
import javax.transaction.Transactional

@Service
class ProductServiceImpl(
    private val productRepository: ProductRepository,
    private val productImageRepository: ProductImageRepository,
    private val categoryRepository: CategoryRepository,
    private val productLikeRepository: ProductLikeRepository
): ProductService {

    @Transactional
    override fun getAllProduct(city: String): Mono<List<ProductResponse>> =
        Mono.justOrEmpty(productRepository.findAllByCityEquals(city).map { productEntity ->
            val images = productImageRepository
                .findAllByProductIdxEquals(productEntity.idx!!)
                .map { it.imageUrl!! }

            val likeUsers = productLikeRepository
                .findAllByProductIdxEquals(productEntity.idx!!)
                .map { it.userIdx!! }

            productEntity.toResponse(images, likeUsers)
        }).switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.NOT_FOUND, "조회 실패")))

    @Transactional
    override fun getProduct(idx: Int): Mono<ProductResponse> =
        Mono.justOrEmpty(
            productRepository.findByIdxEquals(idx)?.toResponse(
                productImageRepository.findAllByProductIdxEquals(idx).map { it.imageUrl!! },
                productLikeRepository.findAllByProductIdxEquals(idx).map { it.userIdx!! },
            )
        ).switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.NOT_FOUND, "조회 실패")))

    @Transactional
    override fun getAllCategory(): Mono<List<CategoryEntity>> =
        Mono.justOrEmpty(categoryRepository.findAll())
            .switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.NOT_FOUND, "조회 실패")))

    @Transactional
    override fun getAllLikeProduct(userIdx: Int): Mono<List<ProductResponse>> =
        Mono.justOrEmpty(productLikeRepository.findAllByUserIdxEquals(userIdx))
            .flatMap { likeEntityList ->
                Mono.justOrEmpty(likeEntityList.mapNotNull { likeEntity ->
                    productRepository.findByIdxEquals(likeEntity.productIdx!!)
                })
            }.switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.NOT_FOUND, "조회 실패")))
            .flatMap { productEntityList ->
                Mono.justOrEmpty(productEntityList.map { productEntity ->
                    val images = productImageRepository
                        .findAllByProductIdxEquals(productEntity.idx!!)
                        .map { it.imageUrl!! }

                    val likeUsers = productLikeRepository
                        .findAllByProductIdxEquals(productEntity.idx!!)
                        .map { it.userIdx!! }

                    productEntity.toResponse(images, likeUsers)
                })
            }.switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.NOT_FOUND, "조회 실패")))

    @Transactional
    override fun saveProduct(productRequest: ProductRequest): Mono<Unit> =
        Mono.justOrEmpty(productRepository.save(productRequest.toEntity()))
            .switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.UNPROCESSABLE_ENTITY, "저장 실패")))
            .flatMap { saveProductImage(productRequest.imageList, it.idx) }

    @Transactional
    override fun saveProductImage(imageList: List<String>?, idx: Int?): Mono<Unit> =
        Mono.justOrEmpty(
            imageList?.map { image ->
                ProductImageEntity().apply {
                    this.productIdx = idx
                    this.imageUrl = image
                }
            }?.map(productImageRepository::save)
        ).switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.UNPROCESSABLE_ENTITY, "저장 실패")))
         .flatMap { Mono.just(Unit) }

    @Transactional
    override fun likeProduct(productIdx: Int, userIdx: Int): Mono<Unit> {
        val entity = productLikeRepository.findByProductIdxAndUserIdx(productIdx, userIdx)

        return if (entity == null) {
            Mono.justOrEmpty(productLikeRepository.save(ProductLikeEntity().apply {
                this.productIdx = productIdx
                this.userIdx = userIdx
            })).switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.UNPROCESSABLE_ENTITY, "저장 실패")))
                .flatMap { Mono.just(Unit) }
        } else {
            Mono.justOrEmpty(productLikeRepository.delete(entity))
        }
    }

    @Transactional
    override fun updateProduct(idx: Int, productRequest: ProductRequest): Mono<Unit> =
        Mono.justOrEmpty(productImageRepository.deleteAllByProductIdxEquals(idx)).flatMap {
            Mono.justOrEmpty(productRepository.save(productRequest.toEntity().apply { this.idx = idx }))
                .switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.UNPROCESSABLE_ENTITY, "저장 실패")))
        }.flatMap { saveProductImage(productRequest.imageList, idx) }

    @Transactional
    override fun updateSold(idx: Int): Mono<Unit> =
        Mono.justOrEmpty(productRepository.findByIdxEquals(idx))
            .flatMap {
                val entity = it.apply { it.idx = idx }
                if (entity.isSold == 0) {
                    entity.isSold = 1
                } else {
                    entity.isSold = 0
                }
                Mono.justOrEmpty(productRepository.save(entity))
            }.flatMap {
                Mono.just(Unit)
            }.switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.UNPROCESSABLE_ENTITY, "저장 실패")))

    @Transactional
    override fun deleteProduct(idx: Int): Mono<Unit> =
        Mono.justOrEmpty(productRepository.deleteByIdxEquals(idx))
            .flatMap {
                if (it == 0) Mono.error(HttpClientErrorException(HttpStatus.UNPROCESSABLE_ENTITY, "저장 실패"))
                else Mono.just(Unit)
            }
}