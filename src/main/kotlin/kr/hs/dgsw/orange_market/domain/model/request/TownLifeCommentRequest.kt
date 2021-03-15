package kr.hs.dgsw.orange_market.domain.model.request

import kr.hs.dgsw.orange_market.domain.entity.TownLifeCommentEntity
import java.time.LocalDateTime

class TownLifeCommentRequest {
    var townLifeIdx: Int? = null
    var comment: String? = null
    var createAt: LocalDateTime? = null
    var location: String? = null
    var userId: String? = null
}

fun TownLifeCommentRequest.toEntity(): TownLifeCommentEntity {
    return TownLifeCommentEntity().apply {
        this.townLifeIdx = this@toEntity.townLifeIdx
        this.comment = this@toEntity.comment
        this.createAt = this@toEntity.createAt
        this.location = this@toEntity.location
        this.userId = this@toEntity.userId
    }
}