package kr.hs.dgsw.orange_market.domain.entity.product

import javax.persistence.*

@Entity
@Table(name = "product_image")
class ProductImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx", nullable = false)
    var idx: Int? = null

    @Column(name = "post_idx", nullable = false)
    var postIdx: Int? = null

    @Column(name = "image_url", nullable = false)
    var imageUrl: String? = null
}