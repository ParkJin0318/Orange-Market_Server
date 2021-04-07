package kr.hs.dgsw.orange_market.domain.response.user

data class UserResponse(
    var idx: Int,
    var userId: String,
    var name: String,
    var city: String,
    var location: String,
    var profileImage: String
)