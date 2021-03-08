package kr.hs.dgsw.orange_market.service.jwt

import io.jsonwebtoken.*
import kr.hs.dgsw.orange_market.domain.entity.UserEntity
import kr.hs.dgsw.orange_market.domain.repository.UserRepository
import kr.hs.dgsw.orange_market.util.Constants
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
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

    override fun createToken(idx: Int): String {
        val secretKey: String? = secretAccessKey
        var expiredAt: Date = Date()
        expiredAt = Date(expiredAt.time + Constants.MILLISECONDS_FOR_A_HOUR * 1)

        val signInKey: Key = SecretKeySpec(secretKey!!.toByteArray(), signatureAlgorithm.jcaName)

        val headerMap: MutableMap<String, Any> = HashMap()
        headerMap["typ"] = "JWT"
        headerMap["alg"] = "HS256"

        val map: MutableMap<String, Any> = HashMap()
        map["idx"] = idx

        val builder: JwtBuilder = Jwts.builder().setHeaderParams(headerMap)
            .setClaims(map)
            .setExpiration(expiredAt)
            .signWith(signInKey)

        return builder.compact()
    }

    override fun validateToken(token: String): UserEntity {
        try {
            val signatureAlgorithm: SignatureAlgorithm = SignatureAlgorithm.HS256

            val signingKey: Key = SecretKeySpec(secretAccessKey!!.toByteArray(), signatureAlgorithm.jcaName)
            val claims: Claims = Jwts
                .parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .body
            return userRepository.findByIdx(claims["idx"].toString().toInt())
                ?: throw HttpClientErrorException(HttpStatus.NOT_FOUND, "유저 없음")

        } catch (e: ExpiredJwtException) {
            throw HttpClientErrorException(HttpStatus.GONE, "토큰 만료")
        } catch (e: SignatureException) {
            throw HttpClientErrorException(HttpStatus.UNAUTHORIZED, "토큰 위조")
        } catch (e: MalformedJwtException) {
            throw HttpClientErrorException(HttpStatus.UNAUTHORIZED, "토큰 위조")
        } catch (e: IllegalArgumentException) {
            throw HttpClientErrorException(HttpStatus.BAD_REQUEST, "토큰 없음")
        } catch (e: Exception) {
            e.printStackTrace()
            throw HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류")
        }
    }
}