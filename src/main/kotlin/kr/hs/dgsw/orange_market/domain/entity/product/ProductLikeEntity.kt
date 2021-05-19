package kr.hs.dgsw.orange_market.domain.entity.product

import javax.persistence.*

@Entity
@Table(name = "product_like")
class ProductLikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    var idx: Int? = null

    @Column(name = "product_idx")
    var postIdx: Int? = null

    @Column(name = "user_idx")
    var userIdx: Int? = null
}