package kr.hs.dgsw.orange_market.domain.mapper

import kr.hs.dgsw.orange_market.domain.entity.local.LocalCommentEntity
import kr.hs.dgsw.orange_market.domain.entity.local.LocalPostEntity
import kr.hs.dgsw.orange_market.domain.request.local.LocalCommentRequest
import kr.hs.dgsw.orange_market.domain.request.local.LocalPostRequest
import kr.hs.dgsw.orange_market.extension.toStringFormat
import java.util.*

/**
 * Request -> Entity
 */
fun LocalPostRequest.toEntity(): LocalPostEntity {
    return LocalPostEntity().apply {
        this.topic = this@toEntity.topic
        this.contents = this@toEntity.contents
        this.createAt = Date().toStringFormat()
        this.userIdx = this@toEntity.userIdx
        this.city = this@toEntity.city
    }
}

/**
 * Request -> Entity
 */
fun LocalCommentRequest.toEntity(): LocalCommentEntity {
    return LocalCommentEntity().apply {
        this.postIdx = this@toEntity.townLifeIdx
        this.comment = this@toEntity.comment
        this.createAt = Date().toString()
        this.userIdx = this@toEntity.userIdx
    }
}