package kr.hs.dgsw.orange_market.service.auth

import kr.hs.dgsw.orange_market.domain.model.request.LoginRequest

interface AuthService {
    fun login(loginRequest: LoginRequest): String?
}