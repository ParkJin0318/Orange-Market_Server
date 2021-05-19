package kr.hs.dgsw.orange_market.service.local

import kr.hs.dgsw.orange_market.domain.entity.local.LocalCommentEntity
import kr.hs.dgsw.orange_market.domain.entity.local.LocalPostEntity
import kr.hs.dgsw.orange_market.domain.mapper.toEntity
import kr.hs.dgsw.orange_market.domain.repository.local.LocalCommentRepository
import kr.hs.dgsw.orange_market.domain.repository.local.LocalPostRepository
import kr.hs.dgsw.orange_market.domain.request.local.LocalCommentRequest
import kr.hs.dgsw.orange_market.domain.request.local.LocalPostRequest
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import reactor.core.publisher.Mono
import javax.transaction.Transactional

@Service
class LocalServiceImpl(
    private val localPostRepository: LocalPostRepository,
    private val localCommentRepository: LocalCommentRepository
): LocalService {

    @Transactional
    override fun getAllLocalPost(city: String): Mono<List<LocalPostEntity>> =
        Mono.justOrEmpty(localPostRepository.findAllByCityEquals(city))
            .switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.NOT_FOUND, "조회 실패")))

    @Transactional
    override fun getLocalPost(idx: Int): Mono<LocalPostEntity> =
        Mono.justOrEmpty(localPostRepository.findByIdxEquals(idx))
            .switchIfEmpty(Mono.error(HttpClientErrorException(HttpStatus.NOT_FOUND, "조회 실패")))

    @Transactional
    override fun getAllLocalComment(postIdx: Int): Mono<List<LocalCommentEntity>> =
        Mono.justOrEmpty(localCommentRepository.findAllByPostIdx(postIdx))
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