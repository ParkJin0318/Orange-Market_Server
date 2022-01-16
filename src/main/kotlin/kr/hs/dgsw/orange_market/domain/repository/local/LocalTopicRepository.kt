package kr.hs.dgsw.orange_market.domain.repository.local

import kr.hs.dgsw.orange_market.domain.entity.local.LocalTopicEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LocalTopicRepository: JpaRepository<LocalTopicEntity, Any> {
    fun findByIdxEquals(idx: Int): LocalTopicEntity?
}