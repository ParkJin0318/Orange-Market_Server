package kr.hs.dgsw.orange_market.domain.entity

import java.sql.Timestamp
import javax.persistence.*

@Entity
@Table(name = "town_life")
class TownLifeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    var idx: Int? = null

    @Column(name = "topic")
    var topic: String? = null

    @Column(name = "contents")
    var contents: String? = null

    @Column(name = "city")
    var city: String? = null

    @Column(name = "location")
    var location: String? = null

    @Column(name = "create_at")
    var createAt: Timestamp? = null

    @Column(name = "user_id")
    var userId: String? = null
}