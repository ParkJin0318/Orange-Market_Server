package kr.hs.dgsw.orange_market.domain.entity.local

import javax.persistence.*

@Entity
@Table(name = "local_topic")
class LocalTopicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx", nullable = false)
    var idx: Int? = null

    @Column(name = "name", nullable = false)
    var name: String? = null
}