package kr.hs.dgsw.orange_market.handler

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
        Mono.justOrEmpty(request.queryParam("city"))
            .switchIfEmpty(Mono.error(Exception("Bad Request")))
            .flatMap(productService::getAllProduct)
            .switchIfEmpty(Mono.error(Exception("조회 실패")))
            .flatMap {
                ResponseData("조회 성공", it).toServerResponse()
            }.onErrorResume {
                Response(it.message).toServerResponse()
            }

    fun get(request: ServerRequest): Mono<ServerResponse> =
        Mono.justOrEmpty(request.pathVariable("idx").toInt())
            .switchIfEmpty(Mono.error(Exception("Bad Request")))
            .flatMap(productService::getProduct)
            .switchIfEmpty(Mono.error(Exception("조회 실패")))
            .flatMap {
                ResponseData("조회 성공", it).toServerResponse()
            }.onErrorResume {
                Response(it.message).toServerResponse()
            }

    fun save(request: ServerRequest): Mono<ServerResponse> =
        request.bodyToMono(ProductRequest::class.java)
            .switchIfEmpty(Mono.error(Exception("Bad Request")))
            .flatMap { productRequest ->
                productService.saveProduct(productRequest).flatMap {
                    productService.saveProductImage(it, productRequest.imageList ?: emptyList())
                }
            }.switchIfEmpty(Mono.error(Exception("등록 실패")))
            .flatMap {
                Response("등록 성공").toServerResponse()
            }.onErrorResume {
                Response(it.message).toServerResponse()
            }

    fun delete(request: ServerRequest): Mono<ServerResponse> =
        Mono.justOrEmpty(request.pathVariable("idx").toInt())
            .switchIfEmpty(Mono.error(Exception("Bad Request")))
            .flatMap(productService::deleteProduct)
            .switchIfEmpty(Mono.error(Exception("삭제 실패")))
            .flatMap {
                Response("삭제 성공").toServerResponse()
            }.onErrorResume {
                Response(it.message).toServerResponse()
            }
}