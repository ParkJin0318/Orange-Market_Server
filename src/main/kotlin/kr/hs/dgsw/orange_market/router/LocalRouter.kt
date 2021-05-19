package kr.hs.dgsw.orange_market.router

import kr.hs.dgsw.orange_market.filter.JwtFilter
import kr.hs.dgsw.orange_market.handler.LocalHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RequestPredicates.path
import org.springframework.web.reactive.function.server.RouterFunctions.nest
import org.springframework.web.reactive.function.server.router

@Configuration
class LocalRouter(
    private val handler: LocalHandler,
    private val jwtFilter: JwtFilter
) {
    @Bean
    fun routerTownLife() = nest(path("/town"),
        router {
            listOf(
                GET("", handler::getAllPost),
                GET("/{idx}", handler::getPost),
                GET("/comment/{idx}", handler::getAllComment),
                POST("", handler::savePost),
                POST("/comment", handler::saveComment),
                PUT("/{idx}", handler::updatePost),
                PUT("/{idx}", handler::updateComment),
                DELETE("/{idx}", handler::deletePost),
                DELETE("/comment/{idx}", handler::deleteComment)
            )
        }.filter(jwtFilter)
    )
}