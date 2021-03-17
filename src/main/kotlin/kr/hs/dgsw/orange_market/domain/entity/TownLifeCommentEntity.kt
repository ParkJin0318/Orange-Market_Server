package kr.hs.dgsw.orange_market.domain.entity

import java.sql.Timestamp
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
    var createAt: Timestamp? = null

    @Column(name = "location")
    var location: String? = null

    @Column(name = "user_id")
    var userId: String? = null
}