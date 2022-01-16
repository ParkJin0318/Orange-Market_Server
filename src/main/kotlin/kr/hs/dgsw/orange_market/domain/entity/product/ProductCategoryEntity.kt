package kr.hs.dgsw.orange_market.domain.entity.product

import javax.persistence.*

@Entity
@Table(name = "product_category")
class ProductCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx", nullable = false)
    var idx: Int? = null

    @Column(name = "name", nullable = false)
    var name: String? = null
}