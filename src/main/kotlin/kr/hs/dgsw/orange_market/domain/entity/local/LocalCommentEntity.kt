package kr.hs.dgsw.orange_market.domain.entity.local

import javax.persistence.*

@Entity
@Table(name = "local_comment")
class LocalCommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    var idx: Int? = null

    @Column(name = "post_idx")
    var postIdx: Int? = null

    @Column(name = "comment")
    var comment: String? = null

    @Column(name = "create_at")
    var createAt: String? = null

    @Column(name = "user_idx")
    var userIdx: Int? = null
}