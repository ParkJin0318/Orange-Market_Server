package kr.hs.dgsw.orange_market.domain.response

import org.springframework.http.HttpStatus

open class Response(
    status: HttpStatus,
    message: String?
) {
    val status: Int = status.value()
    val message: String = message.toString()
}