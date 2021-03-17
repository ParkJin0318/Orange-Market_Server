package kr.hs.dgsw.orange_market.controller

import kr.hs.dgsw.orange_market.domain.entity.UserEntity
import kr.hs.dgsw.orange_market.domain.response.ResponseData
import kr.hs.dgsw.orange_market.service.jwt.JwtServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.HttpClientErrorException

@RestController
@RequestMapping(value = ["/user"])
class UserController {

    @Autowired
    lateinit var jwtService: JwtServiceImpl

    @GetMapping(value = ["/profile"])
    fun getMyProfile(@RequestHeader(value = "token") token: String): ResponseData<UserEntity> {
        try {
            val user: UserEntity = jwtService.validateToken(token)
            return ResponseData(HttpStatus.OK, "프로필 조회 성공", user)
        } catch (e: HttpClientErrorException) {
            throw e
        }
    }
}