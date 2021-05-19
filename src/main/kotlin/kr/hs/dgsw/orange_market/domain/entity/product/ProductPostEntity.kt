package kr.hs.dgsw.orange_market.domain.entity.product

import javax.persistence.*

@Entity
@Table(name = "product_post")
class ProductPostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    var idx: Int? = null

    @Column(name = "category_idx")
    var categoryIdx: Int? = null

    @Column(name = "title")
    var title: String? = null

    @Column(name = "contents")
    var contents: String? = null

    @Column(name = "price")
    var price: String? = null

    @Column(name = "create_at")
    var createAt: String? = null

    @Column(name = "is_sold")
    var isSold: Int? = null

    @Column(name = "user_idx")
    var userIdx: Int? = null

    @Column(name = "city")
    var city: String? = null
}