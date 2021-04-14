package kr.hs.dgsw.orange_market.router

import kr.hs.dgsw.orange_market.handler.UploadHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RequestPredicates.path
import org.springframework.web.reactive.function.server.RouterFunctions.nest
import org.springframework.web.reactive.function.server.router

@Configuration
class UploadRouter(
    private val handler: UploadHandler
) {
    @Bean
    fun routerUpload() = nest(path("/upload"),
        router {
            listOf(
                POST("/image", handler::uploadImage)
            )
        }
    )
}