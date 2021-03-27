package kr.hs.dgsw.orange_market.service.town_life

import kr.hs.dgsw.orange_market.domain.entity.TownLifeEntity
import kr.hs.dgsw.orange_market.domain.request.TownLifeRequest

interface TownLifeService {
    fun getAllTownLife(city: String): List<TownLifeEntity>
    fun saveTownLife(townLifeRequest: TownLifeRequest): Int
}