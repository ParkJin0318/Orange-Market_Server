package kr.hs.dgsw.orange_market.controller

import kr.hs.dgsw.orange_market.domain.entity.UserEntity
import kr.hs.dgsw.orange_market.domain.response.ResponseData
import kr.hs.dgsw.orange_market.service.user.UserServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpServerErrorException

@RestController
@RequestMapping(value = ["/user"])
class UserController {

    @Autowired
    private lateinit var userService: UserServiceImpl

    @GetMapping(value = ["{idx}"])
    fun getUser(@PathVariable("idx") idx: Int): ResponseData<UserEntity> {
        try {
            val user: UserEntity = userService.getUser(idx)
            return ResponseData(HttpStatus.OK, "조회 성공!", user)
        } catch (e: HttpClientErrorException) {
            throw e
        } catch (e: Exception) {
            e.printStackTrace()
            throw HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류")
        }
    }
}