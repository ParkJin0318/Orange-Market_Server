package kr.hs.dgsw.orange_market.service.user

import kr.hs.dgsw.orange_market.domain.entity.UserEntity
import kr.hs.dgsw.orange_market.domain.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException

@Service
class UserServiceImpl: UserService {

    @Autowired
    private lateinit var userRepository: UserRepository

    override fun getUser(idx: Int): UserEntity {
        return userRepository.findByIdx(idx)
            ?: throw HttpClientErrorException(HttpStatus.NOT_FOUND, "Not Found")
    }

    override fun updateLocation(userEntity: UserEntity) {
        userRepository.save(userEntity)
    }
}