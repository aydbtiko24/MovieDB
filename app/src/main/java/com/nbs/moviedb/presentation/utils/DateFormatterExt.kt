package com.nbs.moviedb.presentation.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * Created by aydbtiko on 7/7/2021.
 *
 */
fun Long.toYearQuery(): String {
    Calendar.getInstance().apply {
        clear()
        timeInMillis = this@toYearQuery
        add(Calendar.YEAR, 1)
    }.let { return "${it[Calendar.YEAR]}" }
}

fun String.toYearDate(): String {
    val unknownDate = ""
    if (this.isEmpty()) return unknownDate

    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val date = simpleDateFormat.parse(this)?.time ?: return unknownDate

    return SimpleDateFormat("yyyy", Locale.getDefault()).format(date)
}
