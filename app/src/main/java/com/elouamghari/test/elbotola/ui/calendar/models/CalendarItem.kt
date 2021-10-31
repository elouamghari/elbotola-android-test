package com.elouamghari.test.elbotola.ui.calendar.models

import com.elouamghari.test.elbotola.api.models.Competition
import com.elouamghari.test.elbotola.api.models.Match

sealed class CalendarItem {

    data class CalendarCompetition(
        val competition: Competition
    ) : CalendarItem()

    data class CalendarMatch(
        val match: Match
    ) : CalendarItem()

}