package kr.hs.dgsw.orange_market.service.product

import kr.hs.dgsw.orange_market.domain.entity.product.ProductCategoryEntity
import kr.hs.dgsw.orange_market.domain.entity.product.ProductImageEntity
import kr.hs.dgsw.orange_market.domain.entity.product.ProductLikeEntity
import kr.hs.dgsw.orange_market.domain.entity.product.ProductPostEntity
import kr.hs.dgsw.orange_market.domain.entity.user.UserEntity
import kr.hs.dgsw.orange_market.domain.mapper.toEntity
import kr.hs.dgsw.orange_market.domain.mapper.toResponse
import kr.hs.dgsw.orange_market.domain.repository.product.ProductCategoryRepository
import kr.hs.dgsw.orange_market.domain.repository.product.ProductImageRepository
import kr.hs.dgsw.orange_market.domain.repository.product.ProductLikeRepository
import kr.hs.dgsw.orange_market.domain.repository.product.ProductPostRepository
import kr.hs.dgsw.orange_market.domain.repository.user.UserRepository
import kr.hs.dgsw.orange_market.domain.request.product.ProductPostRequest
import kr.hs.dgsw.orange_market.domain.response.product.ProductResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import reactor.core.publisher.Mono
import javax.transaction.Transactional

@Service
class ProductServiceImpl(
    private val userRepository: UserRepository,
    private val productPostRepository: ProductPostRepository,
    private val productImageRepository: ProductImageRepository,
    private val categoryRepository: ProductCategoryRepository,
    private val productLikeRepository: ProductLikeRepository
): ProductService {

    private fun ProductPostEntity.toResponse(): ProductResponse {
        val user: UserEntity = userRepository
            .findByIdx(this.userIdx!!)!!

        val category: ProductCategoryEntity = categoryRepository
            .findByIdxEquals(this.categoryIdx!!)!!

        val images: List<String> = productImageRepository
            .findAllByPostIdxEquals(this.idx!!)
            .map { it.imageUrl!! }

        val likeUsers: List<Int> = productLikeRepository
            .findAllByPostIdxEquals(this.idx!!)
            .map { it.userIdx!! }

        return this.toResponse(user, category, images, likeUsers)
    }

    @Transactional
    override fun getAllProduct(city: String): Mono<List<ProductResponse>> =
        Mono.justOrEmpty(productPostRepository.findAllByCityEquals(city))
            .switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.NOT_FOUND, "조회 실패")))
            .map { entityList -> entityList.map { it.toResponse() } }

    @Transactional
    override fun getProduct(idx: Int): Mono<ProductResponse> =
        Mono.justOrEmpty(productPostRepository.findByIdxEquals(idx))
            .switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.NOT_FOUND, "조회 실패")))
            .map { it.toResponse() }

    @Transactional
    override fun getAllCategory(): Mono<List<ProductCategoryEntity>> =
        Mono.justOrEmpty(categoryRepository.findAll())
            .switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.NOT_FOUND, "조회 실패")))

    @Transactional
    override fun getAllLikeProduct(userIdx: Int): Mono<List<ProductResponse>> =
        Mono.justOrEmpty(productLikeRepository.findAllByUserIdxEquals(userIdx))
            .map { it.mapNotNull { likeEntity -> productPostRepository.findByIdxEquals(likeEntity.postIdx!!) } }
            .switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.NOT_FOUND, "조회 실패")))
            .map { entityList -> entityList.map { it.toResponse() } }

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
            }.switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.UNPROCESSABLE_ENTITY, "업데이트 실패")))

    @Transactional
    override fun deleteProduct(idx: Int): Mono<Unit> =
        Mono.justOrEmpty(productPostRepository.deleteByIdxEquals(idx))
            .flatMap {
                if (it == 0) Mono.error(HttpClientErrorException(HttpStatus.UNPROCESSABLE_ENTITY, "삭제 실패"))
                else Mono.just(Unit)
            }
}