package kr.hs.dgsw.orange_market.router

import kr.hs.dgsw.orange_market.handler.UserHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RequestPredicates
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.router

@Configuration
class UserRouter(
    private val handler: UserHandler
) {
    @Bean
    fun routerUser() = RouterFunctions.nest(RequestPredicates.path("/user"),
        router {
            listOf(
                GET("/{idx}", handler::getUser),
                POST("/location", handler::updateLocation)
            )
        }
    )
}