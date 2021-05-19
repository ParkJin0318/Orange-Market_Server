package kr.hs.dgsw.orange_market.service.local

import kr.hs.dgsw.orange_market.domain.entity.local.LocalCommentEntity
import kr.hs.dgsw.orange_market.domain.entity.local.LocalPostEntity
import kr.hs.dgsw.orange_market.domain.request.local.LocalCommentRequest
import kr.hs.dgsw.orange_market.domain.request.local.LocalPostRequest
import reactor.core.publisher.Mono

interface LocalService {
    fun getAllLocalPost(city: String): Mono<List<LocalPostEntity>>
    fun getLocalPost(idx: Int): Mono<LocalPostEntity>
    fun getAllLocalComment(postIdx: Int): Mono<List<LocalCommentEntity>>

    fun saveLocalPost(localPostRequest: LocalPostRequest): Mono<Unit>
    fun saveLocalComment(localCommentRequest: LocalCommentRequest): Mono<Unit>

    fun updateLocalPost(idx: Int, localPostRequest: LocalPostRequest): Mono<Unit>
    fun updateLocalComment(idx: Int, localCommentRequest: LocalCommentRequest): Mono<Unit>

    fun deleteLocalPost(idx: Int): Mono<Unit>
    fun deleteLocalComment(idx: Int): Mono<Unit>
}