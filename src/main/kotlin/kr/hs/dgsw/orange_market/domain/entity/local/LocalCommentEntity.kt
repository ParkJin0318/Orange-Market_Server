package kr.hs.dgsw.orange_market.domain.entity.local

import org.hibernate.annotations.CreationTimestamp
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "local_comment")
class LocalCommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx", nullable = false)
    var idx: Int? = null

    @Column(name = "post_idx", nullable = false)
    var postIdx: Int? = null

    @Column(name = "comment", nullable = false)
    var comment: String? = null

    @CreationTimestamp
    @Column(name = "create_at", nullable = false)
    var createAt: Date? = Date()

    @Column(name = "user_idx", nullable = false)
    var userIdx: Int? = null
}