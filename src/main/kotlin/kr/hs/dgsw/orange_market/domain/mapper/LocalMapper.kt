package kr.hs.dgsw.orange_market.domain.mapper

import kr.hs.dgsw.orange_market.domain.entity.local.LocalCommentEntity
import kr.hs.dgsw.orange_market.domain.entity.local.LocalPostEntity
import kr.hs.dgsw.orange_market.domain.entity.local.LocalTopicEntity
import kr.hs.dgsw.orange_market.domain.entity.user.UserEntity
import kr.hs.dgsw.orange_market.domain.request.local.LocalCommentRequest
import kr.hs.dgsw.orange_market.domain.request.local.LocalPostRequest
import kr.hs.dgsw.orange_market.domain.response.local.LocalCommentResponse
import kr.hs.dgsw.orange_market.domain.response.local.LocalPostResponse
import kr.hs.dgsw.orange_market.extension.toStringFormat
import java.util.*

/**
 * Request -> Entity
 */
fun LocalPostRequest.toEntity(): LocalPostEntity {
    return LocalPostEntity().apply {
        this.topicIdx = this@toEntity.topicIdx
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
        this.postIdx = this@toEntity.postIdx
        this.comment = this@toEntity.comment
        this.createAt = Date().toString()
        this.userIdx = this@toEntity.userIdx
    }
}

/**
 * Entity -> Response
 */
fun LocalPostEntity.toResponse(user: UserEntity, topicEntity: LocalTopicEntity): LocalPostResponse =
    LocalPostResponse(
        this.idx!!,
        this.topicIdx!!,
        topicEntity.name!!,
        this.contents!!,
        this.createAt!!,
        this.userIdx!!,
        user.name!!,
        user.location!!,
        user.profileImage ?: "",
        this.city!!
    )

/**
 * Entity -> Response
 */
fun LocalCommentEntity.toResponse(user: UserEntity): LocalCommentResponse =
    LocalCommentResponse(
        this.idx!!,
        this.postIdx!!,
        this.comment!!,
        this.createAt!!,
        this.userIdx!!,
        user.name!!,
        user.location!!,
        user.profileImage ?: ""
    )