package kr.hs.dgsw.orange_market.controller

import kr.hs.dgsw.orange_market.domain.entity.UserEntity
import kr.hs.dgsw.orange_market.domain.request.LocationRequest
import kr.hs.dgsw.orange_market.domain.response.Response
import kr.hs.dgsw.orange_market.domain.response.ResponseData
import kr.hs.dgsw.orange_market.service.user.UserServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpServerErrorException
import javax.servlet.http.HttpServletRequest

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

    @GetMapping(value = ["/profile"])
    fun getUserProfile(request: HttpServletRequest): ResponseData<Any> {
        try {
            val user: UserEntity = request.getAttribute("user") as UserEntity
            return ResponseData(HttpStatus.OK, "프로필 조회 성공", user)
        } catch (e: HttpClientErrorException) {
            throw e
        }
    }

    @PostMapping(value = ["/location"])
    fun updateLocation(
        request: HttpServletRequest,
        @RequestBody locationRequest: LocationRequest
    ): Response {
        try {
            val user: UserEntity = request.getAttribute("user") as UserEntity
            user.city = locationRequest.city
            user.location = locationRequest.location
            userService.updateLocation(user)
            return Response(HttpStatus.OK, "업데이트 성공")
        } catch (e: HttpClientErrorException) {
            throw e
        }
    }
}