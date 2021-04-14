package kr.hs.dgsw.orange_market.handler

import kr.hs.dgsw.orange_market.domain.response.base.ResponseData
import kr.hs.dgsw.orange_market.extension.toServerResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Component
import org.springframework.util.ResourceUtils
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.io.File
import java.util.*

@Component
class UploadHandler {

    @Value("\${spring.web.resources.static-locations}")
    private lateinit var path: String

    fun uploadImage(request: ServerRequest): Mono<ServerResponse> =
        request.multipartData()
            .map { it["file"] }
            .flatMapMany { Flux.fromIterable(it) }
            .cast(FilePart::class.java)
            .next()
            .flatMap { file ->
                val stringBuilder = StringBuilder()
                    .append(Date().time)
                    .append(file.filename())

                val resource: File = ResourceUtils.getFile(path)
                val temp = File("${resource.absolutePath}/${stringBuilder}")

                file.transferTo(temp)
                    .then(Mono.just(stringBuilder.toString()))
            }.flatMap { ResponseData(HttpStatus.OK, "업로드 성공했습니다", it).toServerResponse() }
            .onErrorResume { it.toServerResponse() }
}