package kr.hs.dgsw.orange_market.lib

import org.apache.logging.log4j.util.Strings
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest

@Component
object AuthorizationExtractor {
    fun extract(request: ServerRequest, type: String): String {
        val headers = request.headers().asHttpHeaders()[HttpHeaders.AUTHORIZATION]

        headers?.forEach { value ->
            if (value.toLowerCase().startsWith(type.toLowerCase())) {
                return value.substring(type.length).trim()
            }
        }
        return Strings.EMPTY
    }
}