package kr.hs.dgsw.orange_market.domain.entity

import javax.persistence.*

@Entity
@Table(name = "user")
class UserEntity {
    // 아이디
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    var idx: Int? = null

    @Column(name = "user_id")
    var userId: String? = null

    // 비밀번호
    @Column(name = "user_pw")
    var userPw: String? = null

    // 이름
    @Column(name = "name")
    var name: String? = null

    // 프로필 이미지
    @Column(name = "profile_image")
    var profileImage: String? = null
}