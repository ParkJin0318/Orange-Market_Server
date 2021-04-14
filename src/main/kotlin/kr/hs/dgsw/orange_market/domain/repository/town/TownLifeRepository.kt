package kr.hs.dgsw.orange_market.domain.repository.town

import kr.hs.dgsw.orange_market.domain.entity.town.TownLifeEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TownLifeRepository: JpaRepository<TownLifeEntity, Any> {
    fun findAllByCityEquals(city: String): List<TownLifeEntity>
    fun findByIdxEquals(idx: Int): TownLifeEntity?
    fun deleteByIdxEquals(idx: Int): Int?
}