package com.elouamghari.test.elbotola.extensions

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

fun Date.toDisplayableText(): String{
    return when {
        DateUtils.isToday(this.synchronizeWithApi().time) -> {
            "Today"
        }
        DateUtils.isToday(this.synchronizeWithApi().time + DateUtils.DAY_IN_MILLIS) -> {
            "Yesterday"
        }
        DateUtils.isToday(this.synchronizeWithApi().time - DateUtils.DAY_IN_MILLIS) -> {
            "Tomorrow"
        }
        else -> SimpleDateFormat.getDateInstance().format(this)
    }
}

/**
 * used only to synchronize the app with the api
 */
private fun Date.synchronizeWithApi(): Date{
    // 27-10-2021
    val apiToday = Calendar.getInstance().today().time
    // current date and time
    val realToday = Calendar.getInstance().time.time
    val diff = realToday - apiToday
    return Date(this.time + diff)
}

/**
 * used only to synchronize the app with the api
 */
fun Calendar.today(): Date{
    this.set(2021, 9, 27)
    return this.time
}

