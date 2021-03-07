package kr.hs.dgsw.orange_market.domain.entity

import javax.persistence.*

@Entity
@Table(name = "user")
class UserEntity {
    // 아이디
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: String? = null

    // 비밀번호
    var password: String? = null

    // 이름
    var name: String? = null
}