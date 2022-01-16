package kr.hs.dgsw.orange_market.domain.response.local

import java.util.*

data class LocalCommentResponse(
    val idx: Int,
    val postIdx: Int,
    val comment: String,
    val createAt: Date,
    val userIdx: Int,
    val name: String,
    val location: String,
    val profileImage: String
)