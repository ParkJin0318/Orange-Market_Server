package kr.hs.dgsw.orange_market.service.town_life

import kr.hs.dgsw.orange_market.domain.entity.TownLifeEntity
import kr.hs.dgsw.orange_market.domain.request.TownLifeRequest
import kr.hs.dgsw.orange_market.domain.request.toEntity
import kr.hs.dgsw.orange_market.domain.repository.TownLifeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException

@Service
class TownLifeServiceImpl: TownLifeService {

    @Autowired
    private lateinit var townLifeRepository: TownLifeRepository

    override fun getAllTownLife(city: String): List<TownLifeEntity> {
        return townLifeRepository.findAllByCityEquals(city)
    }

    override fun saveTownLife(townLifeRequest: TownLifeRequest): Int {
        return townLifeRepository.save(townLifeRequest.toEntity()).idx
            ?: throw HttpClientErrorException(HttpStatus.NOT_FOUND, "idx Error")
    }
}