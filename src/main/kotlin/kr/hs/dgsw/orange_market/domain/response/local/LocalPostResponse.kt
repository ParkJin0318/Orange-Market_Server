package kr.hs.dgsw.orange_market.domain.response.local

data class LocalPostResponse(
    val idx: Int,
    val topicIdx: Int,
    val topic: String,
    val contents: String,
    val createAt: String,
    val userIdx: Int,
    val name: String,
    val location: String,
    val profileImage: String,
    val city: String,
    val commentCount: Int
)