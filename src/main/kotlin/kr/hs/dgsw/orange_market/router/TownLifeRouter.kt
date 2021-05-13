package kr.hs.dgsw.orange_market.router

import kr.hs.dgsw.orange_market.filter.JwtFilter
import kr.hs.dgsw.orange_market.handler.TownLifeHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RequestPredicates.path
import org.springframework.web.reactive.function.server.RouterFunctions.nest
import org.springframework.web.reactive.function.server.router

@Configuration
class TownLifeRouter(
    private val handler: TownLifeHandler,
    private val jwtFilter: JwtFilter
) {
    @Bean
    fun routerTownLife() = nest(path("/town"),
        router {
            listOf(
                GET("", handler::getAll),
                GET("/{idx}", handler::get),
                GET("/comment/{idx}", handler::getAllComment),
                POST("", handler::save),
                POST("/comment", handler::saveComment),
                PUT("/{idx}", handler::update),
                DELETE("/{idx}", handler::delete),
                DELETE("/comment/{idx}", handler::deleteComment)
            )
        }.filter(jwtFilter)
    )
}