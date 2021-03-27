package kr.hs.dgsw.orange_market.domain.repository

import kr.hs.dgsw.orange_market.domain.entity.TownLifeCommentEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TownLifeCommentRepository: JpaRepository<TownLifeCommentEntity, Any> {
    fun findAllByTownLifeIdx(townLifeIdx: Int): List<TownLifeCommentEntity>
}