package kr.hs.dgsw.orange_market.service.product

import kr.hs.dgsw.orange_market.domain.entity.product.ProductCategoryEntity
import kr.hs.dgsw.orange_market.domain.entity.product.ProductImageEntity
import kr.hs.dgsw.orange_market.domain.entity.product.ProductLikeEntity
import kr.hs.dgsw.orange_market.domain.mapper.toEntity
import kr.hs.dgsw.orange_market.domain.mapper.toResponse
import kr.hs.dgsw.orange_market.domain.repository.product.ProductCategoryRepository
import kr.hs.dgsw.orange_market.domain.repository.product.ProductImageRepository
import kr.hs.dgsw.orange_market.domain.repository.product.ProductLikeRepository
import kr.hs.dgsw.orange_market.domain.repository.product.ProductPostRepository
import kr.hs.dgsw.orange_market.domain.request.product.ProductPostRequest
import kr.hs.dgsw.orange_market.domain.response.product.ProductResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import reactor.core.publisher.Mono
import javax.transaction.Transactional

@Service
class ProductServiceImpl(
    private val productPostRepository: ProductPostRepository,
    private val productImageRepository: ProductImageRepository,
    private val categoryRepository: ProductCategoryRepository,
    private val productLikeRepository: ProductLikeRepository
): ProductService {

    @Transactional
    override fun getAllProduct(city: String): Mono<List<ProductResponse>> =
        Mono.justOrEmpty(productPostRepository.findAllByCityEquals(city).map { productEntity ->
            val images = productImageRepository
                .findAllByPostIdxEquals(productEntity.idx!!)
                .map { it.imageUrl!! }

            val likeUsers = productLikeRepository
                .findAllByPostIdxEquals(productEntity.idx!!)
                .map { it.userIdx!! }

            productEntity.toResponse(images, likeUsers)
        }).switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.NOT_FOUND, "조회 실패")))

    @Transactional
    override fun getProduct(idx: Int): Mono<ProductResponse> =
        Mono.justOrEmpty(
            productPostRepository.findByIdxEquals(idx)?.toResponse(
                productImageRepository.findAllByPostIdxEquals(idx).map { it.imageUrl!! },
                productLikeRepository.findAllByPostIdxEquals(idx).map { it.userIdx!! },
            )
        ).switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.NOT_FOUND, "조회 실패")))

    @Transactional
    override fun getAllCategory(): Mono<List<ProductCategoryEntity>> =
        Mono.justOrEmpty(categoryRepository.findAll())
            .switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.NOT_FOUND, "조회 실패")))

    @Transactional
    override fun getAllLikeProduct(userIdx: Int): Mono<List<ProductResponse>> =
        Mono.justOrEmpty(productLikeRepository.findAllByUserIdxEquals(userIdx))
            .flatMap { likeEntityList ->
                Mono.justOrEmpty(likeEntityList.mapNotNull { likeEntity ->
                    productPostRepository.findByIdxEquals(likeEntity.postIdx!!)
                })
            }.switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.NOT_FOUND, "조회 실패")))
            .flatMap { productEntityList ->
                Mono.justOrEmpty(productEntityList.map { productEntity ->
                    val images = productImageRepository
                        .findAllByPostIdxEquals(productEntity.idx!!)
                        .map { it.imageUrl!! }

                    val likeUsers = productLikeRepository
                        .findAllByPostIdxEquals(productEntity.idx!!)
                        .map { it.userIdx!! }

                    productEntity.toResponse(images, likeUsers)
                })
            }.switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.NOT_FOUND, "조회 실패")))

    @Transactional
    override fun saveProduct(productPostRequest: ProductPostRequest): Mono<Unit> =
        Mono.justOrEmpty(productPostRepository.save(productPostRequest.toEntity()))
            .switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.UNPROCESSABLE_ENTITY, "저장 실패")))
            .flatMap { saveProductImage(productPostRequest.imageList, it.idx) }

    @Transactional
    override fun saveProductImage(imageList: List<String>?, idx: Int?): Mono<Unit> =
        Mono.justOrEmpty(
            imageList?.map { image ->
                ProductImageEntity().apply {
                    this.postIdx = idx
                    this.imageUrl = image
                }
            }?.map(productImageRepository::save)
        ).switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.UNPROCESSABLE_ENTITY, "저장 실패")))
         .flatMap { Mono.just(Unit) }

    @Transactional
    override fun likeProduct(productIdx: Int, userIdx: Int): Mono<Unit> {
        val entity = productLikeRepository.findByPostIdxAndUserIdx(productIdx, userIdx)

        return if (entity == null) {
            Mono.justOrEmpty(productLikeRepository.save(ProductLikeEntity().apply {
                this.postIdx = productIdx
                this.userIdx = userIdx
            })).switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.UNPROCESSABLE_ENTITY, "저장 실패")))
                .flatMap { Mono.just(Unit) }
        } else {
            Mono.justOrEmpty(productLikeRepository.delete(entity))
        }
    }

    @Transactional
    override fun updateProduct(idx: Int, productPostRequest: ProductPostRequest): Mono<Unit> =
        Mono.justOrEmpty(productImageRepository.deleteAllByPostIdxEquals(idx)).flatMap {
            Mono.justOrEmpty(productPostRepository.save(productPostRequest.toEntity().apply { this.idx = idx }))
                .switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.UNPROCESSABLE_ENTITY, "저장 실패")))
        }.flatMap { saveProductImage(productPostRequest.imageList, idx) }

    @Transactional
    override fun updateSold(idx: Int): Mono<Unit> =
        Mono.justOrEmpty(productPostRepository.findByIdxEquals(idx))
            .flatMap {
                val entity = it.apply {
                    this.idx = idx
                    this.isSold = if (this.isSold == 0) 1 else 0
                }
                Mono.justOrEmpty(productPostRepository.save(entity))
            }.flatMap {
                Mono.just(Unit)
            }.switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.UNPROCESSABLE_ENTITY, "저장 실패")))

    @Transactional
    override fun deleteProduct(idx: Int): Mono<Unit> =
        Mono.justOrEmpty(productPostRepository.deleteByIdxEquals(idx))
            .flatMap {
                if (it == 0) Mono.error(HttpClientErrorException(HttpStatus.UNPROCESSABLE_ENTITY, "저장 실패"))
                else Mono.just(Unit)
            }
}