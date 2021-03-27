package kr.hs.dgsw.orange_market.extension

import java.text.SimpleDateFormat
import java.util.*

fun Date.toFormat(): String {
    val format = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    return format.format(this)
}