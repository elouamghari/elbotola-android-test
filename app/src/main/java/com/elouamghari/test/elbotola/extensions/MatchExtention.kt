package com.elouamghari.test.elbotola.extensions

import com.elouamghari.test.elbotola.api.models.Contestant
import com.elouamghari.test.elbotola.api.models.Match
import com.elouamghari.test.elbotola.constants.Constants
import com.elouamghari.test.elbotola.ui.calendar.models.CalendarItem
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

fun List<Match>.byDate(date: Date): List<Match>{
    val dateString = SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault())
        .format(date)
    return this.byDate(dateString)
}

fun List<Match>.byDate(date: String): List<Match>{
    return this.filter{
        it.date.contains(date)
    }
}

fun List<Match>.live(): List<Match>{
    return this.filter {
        it.matchDetails.matchStatus.lowercase() == "playing"
    }
}

fun List<Match>.orderByTime(): List<Match>{
    return this.sortedBy {
        it.time
    }
}

fun List<Match>.toCalendarItems(): List<CalendarItem>{
    val map = this.groupBy(Match::competition)
    val calendarItems: MutableList<CalendarItem> = ArrayList()
    for (mapItem in map){
        calendarItems.add(CalendarItem.CalendarCompetition(mapItem.key))
        calendarItems.addAll(mapItem.value.map {
            CalendarItem.CalendarMatch(it)
        })
    }
    return calendarItems
}

fun List<Match>.getContestants(): List<Contestant>{
    val contestants : MutableSet<Contestant> = HashSet()
    for (match in this){
        contestants.addAll(match.contestant)
    }
    return contestants.toList()
}