package kr.hs.dgsw.orange_market.domain.mapper

import kr.hs.dgsw.orange_market.domain.entity.town.TownLifeCommentEntity
import kr.hs.dgsw.orange_market.domain.entity.town.TownLifeEntity
import kr.hs.dgsw.orange_market.domain.request.town.TownLifeCommentRequest
import kr.hs.dgsw.orange_market.domain.request.town.TownLifeRequest
import java.util.*

/**
 * Request -> Entity
 */
fun TownLifeRequest.toEntity(): TownLifeEntity {
    return TownLifeEntity().apply {
        this.topic = this@toEntity.topic
        this.contents = this@toEntity.contents
        this.city = this@toEntity.city
        this.location = this@toEntity.location
        this.createAt = Date().toString()
        this.userIdx = this@toEntity.userIdx
    }
}

/**
 * Request -> Entity
 */
fun TownLifeCommentRequest.toEntity(): TownLifeCommentEntity {
    return TownLifeCommentEntity().apply {
        this.townLifeIdx = this@toEntity.townLifeIdx
        this.comment = this@toEntity.comment
        this.createAt = Date().toString()
        this.location = this@toEntity.location
        this.userIdx = this@toEntity.userIdx
    }
}