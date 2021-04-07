package kr.hs.dgsw.orange_market.service.jwt

import io.jsonwebtoken.*
import kr.hs.dgsw.orange_market.domain.entity.user.UserEntity
import kr.hs.dgsw.orange_market.domain.mapper.toResponse
import kr.hs.dgsw.orange_market.domain.repository.user.UserRepository
import kr.hs.dgsw.orange_market.domain.response.user.UserResponse
import kr.hs.dgsw.orange_market.util.Constants
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import reactor.core.publisher.Mono
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.security.Key
import java.security.SignatureException
import java.util.*
import javax.crypto.spec.SecretKeySpec

@Service
class JwtServiceImpl: JwtService {
    @Autowired
    private lateinit var userRepository: UserRepository

    @Value("\${jwt.secret.access}")
    private val secretAccessKey: String? = null

    val signatureAlgorithm: SignatureAlgorithm = SignatureAlgorithm.HS256

    override fun createToken(idx: Int): Mono<String> {
        val secretKey: String? = secretAccessKey
        var expiredAt: Date = Date()
        expiredAt = Date(expiredAt.time + Constants.MILLISECONDS_FOR_A_HOUR * 1)
        println(expiredAt)

        val signInKey: Key = SecretKeySpec(secretKey!!.toByteArray(), signatureAlgorithm.jcaName)

        val headerMap: MutableMap<String, Any> = HashMap()
        headerMap["typ"] = "JWT"
        headerMap["alg"] = "HS256"

        val map: MutableMap<String, Any> = HashMap()
        map["idx"] = idx

        val builder: JwtBuilder = Jwts
            .builder()
            .setHeaderParams(headerMap)
            .setClaims(map)
            .setExpiration(expiredAt)
            .signWith(signInKey)

        return Mono.justOrEmpty(builder.compact())
    }

    override fun validateToken(token: String): Mono<UserResponse> {
        val signatureAlgorithm: SignatureAlgorithm = SignatureAlgorithm.HS256

        val signingKey: Key = SecretKeySpec(secretAccessKey!!.toByteArray(), signatureAlgorithm.jcaName)
        val claims: Claims = Jwts
            .parserBuilder()
            .setSigningKey(signingKey)
            .build()
            .parseClaimsJws(token)
            .body

        return Mono.justOrEmpty(userRepository.findByIdx(claims["idx"].toString().toInt()).map {
            it.toResponse()
        })
    }
}