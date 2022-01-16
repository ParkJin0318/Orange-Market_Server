package kr.hs.dgsw.orange_market.domain.entity.product

import javax.persistence.*

@Entity
@Table(name = "product_like")
class ProductLikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx", nullable = false)
    var idx: Int? = null

    @Column(name = "post_idx", nullable = false)
    var postIdx: Int? = null

    @Column(name = "user_idx", nullable = false)
    var userIdx: Int? = null
}