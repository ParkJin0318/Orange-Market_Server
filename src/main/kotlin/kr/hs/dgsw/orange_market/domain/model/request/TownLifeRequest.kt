package kr.hs.dgsw.orange_market.domain.model.request

import kr.hs.dgsw.orange_market.domain.entity.TownLifeEntity
import java.time.LocalDateTime

class TownLifeRequest {
    var topic: String? = null
    var contents: String? = null
    var city: String? = null
    var location: String? = null
    var createAt: LocalDateTime? = null
    var userId: String? = null
}

fun TownLifeRequest.toEntity(): TownLifeEntity {
    return TownLifeEntity().apply {
        this.topic = this@toEntity.topic
        this.contents = this@toEntity.contents
        this.city = this@toEntity.city
        this.location = this@toEntity.location
        this.createAt = this@toEntity.createAt
        this.userId = this@toEntity.userId
    }
}