package kr.hs.dgsw.orange_market.domain.entity.user

import javax.persistence.*

@Entity
@Table(name = "user")
class UserEntity {
    // 아이디
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx", nullable = false)
    var idx: Int? = null

    @Column(name = "user_id", nullable = false)
    var userId: String? = null

    // 비밀번호
    @Column(name = "user_pw", nullable = false)
    var userPw: String? = null

    // 이름
    @Column(name = "name", nullable = false)
    var name: String? = null

    // 도시
    @Column(name = "city", nullable = false)
    var city: String? = null

    // 위치
    @Column(name = "location", nullable = false)
    var location: String? = null

    // 프로필 이미지
    @Column(name = "profile_image", nullable = false)
    var profileImage: String? = null
}