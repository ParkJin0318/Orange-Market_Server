package kr.hs.dgsw.orange_market.domain.entity.local

import javax.persistence.*

@Entity
@Table(name = "local_post")
class LocalPostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    var idx: Int? = null

    @Column(name = "topic")
    var topic: String? = null

    @Column(name = "contents")
    var contents: String? = null

    @Column(name = "create_at")
    var createAt: String? = null

    @Column(name = "user_idx")
    var userIdx: Int? = null

    @Column(name = "city")
    var city: String? = null
}