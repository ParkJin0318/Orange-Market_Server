package kr.hs.dgsw.orange_market.domain.response.base

open class Response(
    message: String?
) {
    val message: String = message.toString()
}