package kr.hs.dgsw.orange_market.controller

import kr.hs.dgsw.orange_market.domain.model.request.LoginRequest
import kr.hs.dgsw.orange_market.domain.model.response.LoginData
import kr.hs.dgsw.orange_market.domain.model.response.ResponseData
import kr.hs.dgsw.orange_market.domain.repository.UserRepository
import kr.hs.dgsw.orange_market.service.auth.AuthServiceImpl
import kr.hs.dgsw.orange_market.service.jwt.JwtServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpServerErrorException
import javax.validation.Valid

@RestController
@RequestMapping(value = ["/auth"])
class AuthController {

    @Autowired
    lateinit var authService: AuthServiceImpl

    @Autowired
    lateinit var jwtService: JwtServiceImpl

    @GetMapping(value = ["/login"])
    fun login(@RequestBody loginRequest: LoginRequest): ResponseData<LoginData> {
        try {
            val userId: String? = authService.login(loginRequest)
            val accessToken: String = jwtService.createToken(userId!!)
            val data = LoginData(accessToken)
            return ResponseData(HttpStatus.OK, "로그인 성공", data)
        } catch (e: HttpClientErrorException) {
            throw e
        } catch (e: Exception) {
            e.printStackTrace()
            throw HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류")
        }
    }
}