package kr.hs.dgsw.orange_market.domain.entity.product

import org.hibernate.annotations.CreationTimestamp
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "product_post")
class ProductPostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx", nullable = false)
    var idx: Int? = null

    @Column(name = "category_idx", nullable = false)
    var categoryIdx: Int? = null

    @Column(name = "title", nullable = false)
    var title: String? = null

    @Column(name = "contents", nullable = false)
    var contents: String? = null

    @Column(name = "price", nullable = false)
    var price: String? = null

    @CreationTimestamp
    @Column(name = "create_at", nullable = false)
    var createAt: Date? = Date()

    @Column(name = "is_sold", nullable = false)
    var isSold: Int? = null

    @Column(name = "user_idx", nullable = false)
    var userIdx: Int? = null

    @Column(name = "city", nullable = false)
    var city: String? = null
}