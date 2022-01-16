package kr.hs.dgsw.orange_market.service.local

import kr.hs.dgsw.orange_market.domain.entity.local.LocalTopicEntity
import kr.hs.dgsw.orange_market.domain.request.local.LocalCommentRequest
import kr.hs.dgsw.orange_market.domain.request.local.LocalPostRequest
import kr.hs.dgsw.orange_market.domain.response.local.LocalCommentResponse
import kr.hs.dgsw.orange_market.domain.response.local.LocalPostResponse
import reactor.core.publisher.Mono

interface LocalService {
    fun getAllLocalPost(city: String): Mono<List<LocalPostResponse>>
    fun getLocalPost(idx: Int): Mono<LocalPostResponse>
    fun getAllLocalComment(postIdx: Int): Mono<List<LocalCommentResponse>>
    fun getAllTopic(): Mono<List<LocalTopicEntity>>

    fun saveLocalPost(localPostRequest: LocalPostRequest): Mono<Unit>
    fun saveLocalComment(localCommentRequest: LocalCommentRequest): Mono<Unit>

    fun updateLocalPost(idx: Int, localPostRequest: LocalPostRequest): Mono<Unit>
    fun updateLocalComment(idx: Int, localCommentRequest: LocalCommentRequest): Mono<Unit>

    fun deleteLocalPost(idx: Int): Mono<Unit>
    fun deleteLocalComment(idx: Int): Mono<Unit>
}