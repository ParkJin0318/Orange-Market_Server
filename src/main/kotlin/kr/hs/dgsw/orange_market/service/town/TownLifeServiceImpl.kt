package kr.hs.dgsw.orange_market.service.town

import kr.hs.dgsw.orange_market.domain.entity.town.TownLifeCommentEntity
import kr.hs.dgsw.orange_market.domain.entity.town.TownLifeEntity
import kr.hs.dgsw.orange_market.domain.mapper.toEntity
import kr.hs.dgsw.orange_market.domain.repository.town.TownLifeCommentRepository
import kr.hs.dgsw.orange_market.domain.repository.town.TownLifeRepository
import kr.hs.dgsw.orange_market.domain.request.town.TownLifeRequest
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import javax.transaction.Transactional

@Service
class TownLifeServiceImpl(
    private val townLifeRepository: TownLifeRepository,
    private val townLifeCommentRepository: TownLifeCommentRepository
): TownLifeService {

    @Transactional
    override fun getAllTownLife(city: String): Mono<List<TownLifeEntity>> =
        Mono.justOrEmpty(townLifeRepository.findAllByCityEquals(city))
            .switchIfEmpty(Mono.error(Exception("조회 실패")))

    @Transactional
    override fun getTownLife(idx: Int): Mono<TownLifeEntity> =
        Mono.justOrEmpty(townLifeRepository.findByIdxEquals(idx))
            .switchIfEmpty(Mono.error(Exception("조회 실패")))

    @Transactional
    override fun getAllTownLifeComment(townLifeIdx: Int): Mono<List<TownLifeCommentEntity>> =
        Mono.justOrEmpty(townLifeCommentRepository.findAllByTownLifeIdx(townLifeIdx))
            .switchIfEmpty(Mono.error(Exception("조회 실패")))

    @Transactional
    override fun saveTownLife(townLifeRequest: TownLifeRequest): Mono<Unit> =
        Mono.justOrEmpty(townLifeRepository.save(townLifeRequest.toEntity()))
            .switchIfEmpty(Mono.error(Exception("저장 실패")))
            .flatMap { Mono.just(Unit) }

    @Transactional
    override fun updateTownLife(idx: Int, townLifeRequest: TownLifeRequest): Mono<Unit> =
        Mono.justOrEmpty(townLifeRepository.save(townLifeRequest.toEntity().apply { this.idx = idx }))
            .switchIfEmpty(Mono.error(Exception("저장 실패")))
            .flatMap { Mono.just(Unit) }

    @Transactional
    override fun deleteTownLife(idx: Int): Mono<Unit> =
        Mono.justOrEmpty(townLifeRepository.deleteByIdxEquals(idx))
            .flatMap {
                if (it == 0) Mono.empty()
                else Mono.just(Unit)
            }.switchIfEmpty(Mono.error(Exception("삭제 실패")))
}