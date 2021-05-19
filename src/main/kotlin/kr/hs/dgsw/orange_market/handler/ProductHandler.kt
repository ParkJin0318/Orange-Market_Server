package kr.hs.dgsw.orange_market.handler

import kr.hs.dgsw.orange_market.domain.entity.user.UserEntity
import kr.hs.dgsw.orange_market.domain.request.product.ProductPostRequest
import kr.hs.dgsw.orange_market.domain.response.base.Response
import kr.hs.dgsw.orange_market.domain.response.base.ResponseData
import kr.hs.dgsw.orange_market.extension.toServerResponse
import kr.hs.dgsw.orange_market.service.product.ProductServiceImpl
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class ProductHandler(
    private val productService: ProductServiceImpl
) {
    fun getAllProduct(request: ServerRequest): Mono<ServerResponse> =
        Mono.justOrEmpty(request.queryParam("city"))
            .switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.BAD_REQUEST, "잚못된 요청")))
            .flatMap(productService::getAllProduct)
            .flatMap { ResponseData(HttpStatus.OK,"조회 성공", it).toServerResponse() }
            .onErrorResume { it.toServerResponse() }

    fun getProduct(request: ServerRequest): Mono<ServerResponse> =
        Mono.justOrEmpty(request.pathVariable("idx").toInt())
            .switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.BAD_REQUEST, "잚못된 요청")))
            .flatMap(productService::getProduct)
            .flatMap { ResponseData(HttpStatus.OK,"조회 성공", it).toServerResponse() }
            .onErrorResume { it.toServerResponse() }

    fun getAllLikeProduct(request: ServerRequest): Mono<ServerResponse> =
        Mono.justOrEmpty(request.attribute("user"))
            .switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.BAD_REQUEST, "잚못된 요청")))
            .cast(UserEntity::class.java)
            .flatMap { user -> productService.getAllLikeProduct(user.idx!!) }
            .flatMap { ResponseData(HttpStatus.OK,"조회 성공", it).toServerResponse() }
            .onErrorResume { it.toServerResponse() }

    fun getAllCategory(request: ServerRequest): Mono<ServerResponse> =
        productService.getAllCategory()
            .flatMap { ResponseData(HttpStatus.OK,"조회 성공", it).toServerResponse() }
            .onErrorResume { it.toServerResponse() }

    fun saveProduct(request: ServerRequest): Mono<ServerResponse> =
        request.bodyToMono(ProductPostRequest::class.java)
            .switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.BAD_REQUEST, "잚못된 요청")))
            .flatMap(productService::saveProduct)
            .switchIfEmpty(Mono.error(Exception("등록 실패")))
            .flatMap { Response(HttpStatus.OK,"등록 성공").toServerResponse() }
            .onErrorResume { it.toServerResponse() }

    fun likeProduct(request: ServerRequest): Mono<ServerResponse> =
        Mono.justOrEmpty(request.pathVariable("idx").toInt())
            .switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.BAD_REQUEST, "잚못된 요청")))
            .flatMap { idx ->
                Mono.justOrEmpty(request.attribute("user"))
                    .switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.BAD_REQUEST, "잚못된 요청")))
                    .cast(UserEntity::class.java)
                    .flatMap { user -> productService.likeProduct(idx, user.idx!!) }
            }.flatMap { Response(HttpStatus.OK,"업데이트 성공").toServerResponse() }
            .onErrorResume { it.toServerResponse() }

    fun updateProduct(request: ServerRequest): Mono<ServerResponse> =
        request.bodyToMono(ProductPostRequest::class.java)
            .switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.BAD_REQUEST, "잚못된 요청")))
            .flatMap { productRequest ->
                val idx = request.pathVariable("idx").toInt()
                productService.updateProduct(idx, productRequest)
            }.switchIfEmpty(Mono.error(Exception("업데이트 실패")))
            .flatMap { Response(HttpStatus.OK,"업데이트 성공").toServerResponse() }
            .onErrorResume { it.toServerResponse() }

    fun updateSold(request: ServerRequest): Mono<ServerResponse> =
        Mono.justOrEmpty(request.pathVariable("idx").toInt())
            .switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.BAD_REQUEST, "잚못된 요청")))
            .flatMap(productService::updateSold)
            .flatMap { Response(HttpStatus.OK,"업데이트 성공").toServerResponse() }
            .onErrorResume { it.toServerResponse() }

    fun deleteProduct(request: ServerRequest): Mono<ServerResponse> =
        Mono.justOrEmpty(request.pathVariable("idx").toInt())
            .switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.BAD_REQUEST, "잚못된 요청")))
            .flatMap(productService::deleteProduct)
            .flatMap { Response(HttpStatus.OK,"삭제 성공").toServerResponse() }
            .onErrorResume { it.toServerResponse() }
}