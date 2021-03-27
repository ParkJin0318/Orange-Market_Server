package kr.hs.dgsw.orange_market.config

import kr.hs.dgsw.orange_market.filter.JwtAuthenticationFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.HandlerExceptionResolver

@Configuration
class JwtFilterConfig {

    @Autowired
    private lateinit var handlerExceptionResolver: HandlerExceptionResolver

    @Bean
    fun authFilter(): FilterRegistrationBean<JwtAuthenticationFilter> {
        val registrationBean: FilterRegistrationBean<JwtAuthenticationFilter> = FilterRegistrationBean<JwtAuthenticationFilter>()
        registrationBean.filter = JwtAuthenticationFilter(handlerExceptionResolver)
        registrationBean.addUrlPatterns("/auth/profile")
        registrationBean.addUrlPatterns("/user/*")
        registrationBean.addUrlPatterns("/product/*")
        registrationBean.order = 2

        return registrationBean
    }
}