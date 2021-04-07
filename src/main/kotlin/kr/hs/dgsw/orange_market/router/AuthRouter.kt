package kr.hs.dgsw.orange_market.router

import kr.hs.dgsw.orange_market.handler.AuthHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RequestPredicates.path
import org.springframework.web.reactive.function.server.RouterFunctions.nest
import org.springframework.web.reactive.function.server.router

@Configuration
class AuthRouter(
    private val handler: AuthHandler
) {
    @Bean
    fun routerAuth() = nest(path("/auth"),
        router {
            listOf(
                POST("/login", handler::login),
                POST("/register", handler::register)
            )
        }
    )
}