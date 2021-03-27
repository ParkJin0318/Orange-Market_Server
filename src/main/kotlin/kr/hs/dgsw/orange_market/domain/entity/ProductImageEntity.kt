package kr.hs.dgsw.orange_market.domain.entity

import javax.persistence.*

@Entity
@Table(name = "product_image")
class ProductImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    var idx: Int? = null

    @Column(name = "product_idx")
    var productIdx: Int? = null

    @Column(name = "image_url")
    var imageUrl: String? = null
}