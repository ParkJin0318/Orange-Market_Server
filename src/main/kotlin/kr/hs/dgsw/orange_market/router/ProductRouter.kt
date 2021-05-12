package kr.hs.dgsw.orange_market.router

import kr.hs.dgsw.orange_market.filter.JwtFilter
import kr.hs.dgsw.orange_market.handler.ProductHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RequestPredicates.path
import org.springframework.web.reactive.function.server.RouterFunctions.nest
import org.springframework.web.reactive.function.server.router

@Configuration
class ProductRouter(
    private val handler: ProductHandler,
    private val jwtFilter: JwtFilter
) {
    @Bean
    fun routerProduct() = nest(path("/product"),
        router {
            listOf(
                GET("/category", handler::getAllCategory),
                GET("/like", handler::getAllLikeProduct),
                GET("", handler::getAll),
                GET("/{idx}", handler::get),
                POST("", handler::save),
                POST("/like/{idx}", handler::likeProduct),
                PUT("/{idx}", handler::update),
                PUT("/sold/{idx}", handler::updateSold),
                DELETE("/{idx}", handler::delete)
            )
        }.filter(jwtFilter)
    )
}