package kr.hs.dgsw.orange_market.handler

import javassist.NotFoundException
import kr.hs.dgsw.orange_market.domain.request.product.ProductRequest
import kr.hs.dgsw.orange_market.domain.response.base.Response
import kr.hs.dgsw.orange_market.domain.response.base.ResponseData
import kr.hs.dgsw.orange_market.extension.toServerResponse
import kr.hs.dgsw.orange_market.service.product.ProductServiceImpl
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class ProductHandler(
    private val productService: ProductServiceImpl
) {
    fun getAll(request: ServerRequest): Mono<ServerResponse> =
        productService.getAllProduct(request.queryParam("city").get())
            .switchIfEmpty(Mono.error(NotFoundException("Not Found")))
            .flatMap {
                ResponseData("조회 성공", it).toServerResponse()
            }.onErrorResume {
                Response(it.message).toServerResponse()
            }

    fun get(request: ServerRequest): Mono<ServerResponse> =
        productService.getProduct(request.pathVariable("idx").toInt())
            .switchIfEmpty(Mono.error(NotFoundException("Not Found")))
            .flatMap {
                ResponseData("조회 성공", it).toServerResponse()
            }.onErrorResume {
                Response(it.message).toServerResponse()
            }

    fun save(request: ServerRequest): Mono<ServerResponse> =
        request.bodyToMono(ProductRequest::class.java).flatMap { productRequest ->
            productService.saveProduct(productRequest).map { idx ->
                productService.saveProductImage(idx, productRequest.imageList ?: emptyList())
            }
        }.flatMap {
            Response("등록 성공").toServerResponse()
        }.onErrorResume {
            Response(it.message).toServerResponse()
        }
}