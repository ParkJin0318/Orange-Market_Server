package kr.hs.dgsw.orange_market.domain.request

import kr.hs.dgsw.orange_market.domain.entity.TownLifeEntity
import java.sql.Timestamp
import java.util.*

class TownLifeRequest {
    var topic: String? = null
    var contents: String? = null
    var city: String? = null
    var location: String? = null
    var createAt: Date? = null
    var userIdx: Int? = null
}

fun TownLifeRequest.toEntity(): TownLifeEntity {
    return TownLifeEntity().apply {
        this.topic = this@toEntity.topic
        this.contents = this@toEntity.contents
        this.city = this@toEntity.city
        this.location = this@toEntity.location
        this.createAt = this@toEntity.createAt as Timestamp?
        this.userIdx = this@toEntity.userIdx
    }
}