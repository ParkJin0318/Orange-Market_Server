package kr.hs.dgsw.orange_market.domain.response.base

class ResponseData<T>(
    message: String,
    val data: T
): Response(message)