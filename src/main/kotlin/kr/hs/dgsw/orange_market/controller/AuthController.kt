package kr.hs.dgsw.orange_market.controller

import kr.hs.dgsw.orange_market.domain.request.LoginRequest
import kr.hs.dgsw.orange_market.domain.request.RegisterRequest
import kr.hs.dgsw.orange_market.domain.response.LoginData
import kr.hs.dgsw.orange_market.domain.response.Response
import kr.hs.dgsw.orange_market.domain.response.ResponseData
import kr.hs.dgsw.orange_market.service.auth.AuthServiceImpl
import kr.hs.dgsw.orange_market.service.jwt.JwtServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpServerErrorException
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping(value = ["/auth"])
class AuthController {

    @Autowired
    lateinit var authService: AuthServiceImpl

    @Autowired
    lateinit var jwtService: JwtServiceImpl

    @PostMapping(value = ["/login"])
    fun login(@RequestBody loginRequest: LoginRequest): ResponseData<LoginData> {
        try {
            val userId: Int? = authService.login(loginRequest)
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

    @PostMapping(value = ["/register"])
    fun register(@RequestBody registerRequest: RegisterRequest): Response {
        try {
            authService.register(registerRequest)
            return Response(HttpStatus.OK, "가입 성공")
        } catch (e: HttpClientErrorException) {
            throw e
        } catch (e: Exception) {
            throw HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류")
        }
    }
}