package kr.hs.dgsw.orange_market.extension

fun Int.toBoolean() =
    when(this) {
        1 -> true
        else -> false
    }