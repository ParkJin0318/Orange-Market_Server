package kr.hs.dgsw.orange_market.lib

import org.apache.logging.log4j.util.Strings
import org.springframework.stereotype.Component
import java.util.*
import javax.servlet.http.HttpServletRequest

@Component
object AuthorizationExtractor {
    private const val TOKEN: String = "token"

    fun extract(request: HttpServletRequest, type: String): String {
        val headers: Enumeration<String> = request.getHeaders(TOKEN)
        while (headers.hasMoreElements()) {
            val value: String = headers.nextElement()
            if (value.toLowerCase().startsWith(type.toLowerCase())) {
                return value.substring(type.length).trim()
            }
        }

        return Strings.EMPTY
    }
}