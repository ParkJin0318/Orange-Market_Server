package kr.hs.dgsw.orange_market.filter

import kr.hs.dgsw.orange_market.domain.entity.UserEntity
import kr.hs.dgsw.orange_market.lib.AuthorizationExtractor
import kr.hs.dgsw.orange_market.service.jwt.JwtServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.context.support.WebApplicationContextUtils
import org.springframework.web.servlet.HandlerExceptionResolver
import javax.servlet.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthenticationFilter(
    private val handlerExceptionResolver: HandlerExceptionResolver
): Filter {

    @Autowired
    private lateinit var jwtService: JwtServiceImpl

    override fun init(filterConfig: FilterConfig) {
        val ctx: ApplicationContext = WebApplicationContextUtils
            .getRequiredWebApplicationContext(filterConfig.servletContext)
        this.jwtService = ctx.getBean(JwtServiceImpl::class.java)
    }

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        try {
            val token: String = AuthorizationExtractor.extract(request as HttpServletRequest, "")

            if (request.method != "OPTIONS") {
                if (token.isEmpty()) {
                    throw HttpClientErrorException(HttpStatus.BAD_REQUEST, "검증 오류.")
                }

                val user: UserEntity = jwtService.validateToken(token)
                request.setAttribute("user", user)
            }

            chain.doFilter(request, response)
        } catch (e: Exception) {
            handlerExceptionResolver.resolveException(request as HttpServletRequest, response as HttpServletResponse, null, e)
        }
    }
}