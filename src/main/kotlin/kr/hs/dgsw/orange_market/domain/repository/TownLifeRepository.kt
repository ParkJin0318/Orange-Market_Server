package kr.hs.dgsw.orange_market.domain.repository

import kr.hs.dgsw.orange_market.domain.entity.TownLifeEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TownLifeRepository: JpaRepository<TownLifeEntity, Any> {
    fun findAllByCityEquals(city: String): List<TownLifeEntity>
}