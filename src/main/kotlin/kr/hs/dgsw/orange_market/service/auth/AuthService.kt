package kr.hs.dgsw.orange_market.service.auth

import kr.hs.dgsw.orange_market.domain.model.request.LoginRequest
import kr.hs.dgsw.orange_market.domain.model.request.RegisterRequest

interface AuthService {
    fun login(loginRequest: LoginRequest): Int?
    fun register(registerRequest: RegisterRequest)
}