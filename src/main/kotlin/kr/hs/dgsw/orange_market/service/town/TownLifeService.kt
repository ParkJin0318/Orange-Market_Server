package kr.hs.dgsw.orange_market.service.town

import kr.hs.dgsw.orange_market.domain.entity.town.TownLifeCommentEntity
import kr.hs.dgsw.orange_market.domain.entity.town.TownLifeEntity
import kr.hs.dgsw.orange_market.domain.request.town.TownLifeRequest
import reactor.core.publisher.Mono

interface TownLifeService {
    fun getAllTownLife(city: String): Mono<List<TownLifeEntity>>
    fun getTownLife(idx: Int): Mono<TownLifeEntity>
    fun getAllTownLifeComment(townLifeIdx: Int): Mono<List<TownLifeCommentEntity>>

    fun saveTownLife(townLifeRequest: TownLifeRequest): Mono<Unit>
    fun updateTownLife(idx: Int, townLifeRequest: TownLifeRequest): Mono<Unit>
    fun deleteTownLife(idx: Int): Mono<Unit>
}