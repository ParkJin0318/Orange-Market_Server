package kr.hs.dgsw.orange_market.domain.entity.town

import javax.persistence.*

@Entity
@Table(name = "town_life_comment")
class TownLifeCommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    var idx: Int? = null

    @Column(name = "town_life_idx")
    var townLifeIdx: Int? = null

    @Column(name = "comment")
    var comment: String? = null

    @Column(name = "create_at")
    var createAt: String? = null

    @Column(name = "location")
    var location: String? = null

    @Column(name = "user_idx")
    var userIdx: Int? = null
}