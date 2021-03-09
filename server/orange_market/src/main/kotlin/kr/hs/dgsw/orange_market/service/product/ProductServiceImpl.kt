package kr.hs.dgsw.orange_market.service.product

import kr.hs.dgsw.orange_market.domain.entity.ProductImageEntity
import kr.hs.dgsw.orange_market.domain.model.request.ProductRequest
import kr.hs.dgsw.orange_market.domain.model.request.toEntity
import kr.hs.dgsw.orange_market.domain.model.response.ProductData
import kr.hs.dgsw.orange_market.domain.repository.ProductImageRepository
import kr.hs.dgsw.orange_market.domain.repository.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException

@Service
class ProductServiceImpl: ProductService {

    @Autowired
    private lateinit var productRepository: ProductRepository

    @Autowired
    private lateinit var productImageRepository: ProductImageRepository

    override fun getAllProduct(): List<ProductData> {
        val products: List<ProductData> = productRepository.findAll().map { entity ->

            val images: List<String?> = productImageRepository
                .findAllByProductIdxEquals(entity.idx!!)
                .map { it.imageUrl }

            return@map ProductData(
                entity.title!!,
                entity.contents!!,
                entity.price!!,
                entity.createAt,
                entity.isSold!!,
                entity.userId!!,
                entity.city!!,
                entity.location!!,
                images
            )
        }

        return products
    }

    override fun saveProduct(productRequest: ProductRequest): Int {
        return productRepository.save(productRequest.toEntity()).idx
            ?: throw HttpClientErrorException(HttpStatus.NOT_FOUND, "idx Error")
    }

    override fun saveProductImage(idx: Int, imageList: List<String>) {
        val entityList: List<ProductImageEntity> = imageList.map { image ->
            ProductImageEntity().apply {
                this.productIdx = idx
                this.imageUrl = image
            }
        }
        productImageRepository.saveAll(entityList)
    }
}