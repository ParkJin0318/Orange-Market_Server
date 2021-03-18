package kr.hs.dgsw.orange_market.domain.request

import kr.hs.dgsw.orange_market.domain.entity.TownLifeCommentEntity
import java.sql.Timestamp
import java.util.*

class TownLifeCommentRequest {
    var townLifeIdx: Int? = null
    var comment: String? = null
    var createAt: Date? = null
    var location: String? = null
    var userIdx: Int? = null
}

fun TownLifeCommentRequest.toEntity(): TownLifeCommentEntity {
    return TownLifeCommentEntity().apply {
        this.townLifeIdx = this@toEntity.townLifeIdx
        this.comment = this@toEntity.comment
        this.createAt = this@toEntity.createAt as Timestamp?
        this.location = this@toEntity.location
        this.userIdx = this@toEntity.userIdx
    }
}