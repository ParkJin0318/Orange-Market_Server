package kr.hs.dgsw.orange_market.service.local

import kr.hs.dgsw.orange_market.domain.entity.local.LocalPostEntity
import kr.hs.dgsw.orange_market.domain.entity.local.LocalTopicEntity
import kr.hs.dgsw.orange_market.domain.entity.user.UserEntity
import kr.hs.dgsw.orange_market.domain.mapper.toEntity
import kr.hs.dgsw.orange_market.domain.mapper.toResponse
import kr.hs.dgsw.orange_market.domain.repository.local.LocalCommentRepository
import kr.hs.dgsw.orange_market.domain.repository.local.LocalPostRepository
import kr.hs.dgsw.orange_market.domain.repository.local.LocalTopicRepository
import kr.hs.dgsw.orange_market.domain.repository.user.UserRepository
import kr.hs.dgsw.orange_market.domain.request.local.LocalCommentRequest
import kr.hs.dgsw.orange_market.domain.request.local.LocalPostRequest
import kr.hs.dgsw.orange_market.domain.response.local.LocalCommentResponse
import kr.hs.dgsw.orange_market.domain.response.local.LocalPostResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import reactor.core.publisher.Mono
import javax.transaction.Transactional

@Service
class LocalServiceImpl(
    private val userRepository: UserRepository,
    private val localPostRepository: LocalPostRepository,
    private val localCommentRepository: LocalCommentRepository,
    private val localTopicRepository: LocalTopicRepository
): LocalService {

    private fun LocalPostEntity.toResponse(): LocalPostResponse {
        val user: UserEntity = userRepository
            .findByIdx(this.userIdx!!)!!

        val topic: LocalTopicEntity = localTopicRepository
            .findByIdxEquals(this.topicIdx!!)!!

        return this.toResponse(user, topic)
    }

    @Transactional
    override fun getAllLocalPost(city: String): Mono<List<LocalPostResponse>> =
        Mono.justOrEmpty(localPostRepository.findAllByCityEquals(city))
            .switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.NOT_FOUND, "조회 실패")))
            .map { entityList -> entityList.map { it.toResponse() } }

    @Transactional
    override fun getLocalPost(idx: Int): Mono<LocalPostResponse> =
        Mono.justOrEmpty(localPostRepository.findByIdxEquals(idx))
            .switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.NOT_FOUND, "조회 실패")))
            .map { it.toResponse() }

    @Transactional
    override fun getAllLocalComment(postIdx: Int): Mono<List<LocalCommentResponse>> =
        Mono.justOrEmpty(localCommentRepository.findAllByPostIdx(postIdx))
            .switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.NOT_FOUND, "조회 실패")))
            .map { entityList ->
                entityList.map {
                    val user: UserEntity = userRepository
                        .findByIdx(it.userIdx!!)!!
                    it.toResponse(user)
                }
            }

    @Transactional
    override fun getAllTopic(): Mono<List<LocalTopicEntity>> =
        Mono.justOrEmpty(localTopicRepository.findAll())
            .switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.NOT_FOUND, "조회 실패")))

    @Transactional
    override fun saveLocalPost(localPostRequest: LocalPostRequest): Mono<Unit> =
        Mono.justOrEmpty(localPostRepository.save(localPostRequest.toEntity()))
            .switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.UNPROCESSABLE_ENTITY, "저장 실패")))
            .flatMap { Mono.just(Unit) }

    @Transactional
    override fun saveLocalComment(localCommentRequest: LocalCommentRequest): Mono<Unit> =
        Mono.justOrEmpty(localCommentRepository.save(localCommentRequest.toEntity()))
            .switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.UNPROCESSABLE_ENTITY, "저장 실패")))
            .flatMap { Mono.just(Unit) }

    @Transactional
    override fun updateLocalPost(idx: Int, localPostRequest: LocalPostRequest): Mono<Unit> =
        Mono.justOrEmpty(localPostRepository.save(localPostRequest.toEntity().apply { this.idx = idx }))
            .switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.UNPROCESSABLE_ENTITY, "저장 실패")))
            .flatMap { Mono.just(Unit) }

    @Transactional
    override fun updateLocalComment(idx: Int, localCommentRequest: LocalCommentRequest): Mono<Unit> =
        Mono.justOrEmpty(localCommentRepository.save(localCommentRequest.toEntity().apply { this.idx = idx }))
            .switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.UNPROCESSABLE_ENTITY, "저장 실패")))
            .flatMap { Mono.just(Unit) }

    @Transactional
    override fun deleteLocalPost(idx: Int): Mono<Unit> =
        Mono.justOrEmpty(localPostRepository.deleteByIdxEquals(idx))
            .flatMap {
                if (it == 0) Mono.empty()
                else Mono.just(Unit)
            }.switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.UNPROCESSABLE_ENTITY, "삭제 실패")))

    @Transactional
    override fun deleteLocalComment(idx: Int): Mono<Unit> =
        Mono.justOrEmpty(localCommentRepository.deleteByIdxEquals(idx))
            .flatMap {
                if (it == 0) Mono.empty()
                else Mono.just(Unit)
            }.switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.UNPROCESSABLE_ENTITY, "삭제 실패")))
}