package kr.hs.dgsw.orange_market.domain.model.response

import org.springframework.http.HttpStatus

class ResponseData<T>(
    status: HttpStatus,
    message: String,
    val data: T
): Response(status, message)