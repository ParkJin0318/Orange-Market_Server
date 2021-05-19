package kr.hs.dgsw.orange_market.domain.repository.local

import kr.hs.dgsw.orange_market.domain.entity.local.LocalCommentEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LocalCommentRepository: JpaRepository<LocalCommentEntity, Any> {
    fun findAllByPostIdx(townLifeIdx: Int): List<LocalCommentEntity>
    fun deleteByIdxEquals(idx: Int): Int?
}