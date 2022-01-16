package kr.hs.dgsw.orange_market.domain.entity.local

import org.hibernate.annotations.CreationTimestamp
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "local_post")
class LocalPostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx", nullable = false)
    var idx: Int? = null

    @Column(name = "topic_idx", nullable = false)
    var topicIdx: Int? = null

    @Column(name = "contents", nullable = false)
    var contents: String? = null

    @CreationTimestamp
    @Column(name = "create_at", nullable = false)
    var createAt: Date? = Date()

    @Column(name = "user_idx", nullable = false)
    var userIdx: Int? = null

    @Column(name = "city", nullable = false)
    var city: String? = null
}