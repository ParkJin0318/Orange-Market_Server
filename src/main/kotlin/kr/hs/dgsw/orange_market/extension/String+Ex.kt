package kr.hs.dgsw.orange_market.extension

import java.text.SimpleDateFormat
import java.util.*

fun Date.toStringFormat(pattern: String = "yyyy-MM-dd HH:mm"): String {
    val format = SimpleDateFormat(pattern, Locale.getDefault())
    return format.format(this)
}