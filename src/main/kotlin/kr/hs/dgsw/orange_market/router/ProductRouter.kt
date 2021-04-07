package kr.hs.dgsw.orange_market.router

import kr.hs.dgsw.orange_market.handler.ProductHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RequestPredicates.path
import org.springframework.web.reactive.function.server.RouterFunctions.nest
import org.springframework.web.reactive.function.server.router

@Configuration
class ProductRouter(
    private val handler: ProductHandler
) {
    @Bean
    fun routerProduct() = nest(path("/product"),
        router {
            listOf(
                GET("", handler::getAll),
                GET("/{idx}", handler::get),
                POST("", handler::save)
            )
        }
    )
}