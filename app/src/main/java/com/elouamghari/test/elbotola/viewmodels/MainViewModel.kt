package com.elouamghari.test.elbotola.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elouamghari.test.elbotola.api.Api
import com.elouamghari.test.elbotola.api.clients.ApiClient
import com.elouamghari.test.elbotola.api.models.Contestant
import com.elouamghari.test.elbotola.api.models.Match
import com.elouamghari.test.elbotola.api.responses.ApiResponse
import com.elouamghari.test.elbotola.extensions.*
import com.elouamghari.test.elbotola.ui.calendar.models.CalendarItem
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.util.*
import kotlin.collections.ArrayList

class MainViewModel : ViewModel() {

    private var allMatches: List<Match> = ArrayList()
    private val calendarMutable : MutableLiveData<List<CalendarItem>> = MutableLiveData()
    private val followedMutable: MutableLiveData<List<Contestant>> = MutableLiveData()
    private var cal: Calendar = Calendar.getInstance()
    private val dateFilterMutable: MutableLiveData<Date> = MutableLiveData(cal.today())

    val dateFilter: LiveData<Date> by lazy {
        dateFilterMutable
    }

    val calendar: LiveData<List<CalendarItem>> by lazy {
        calendarMutable
    }

    val followed: LiveData<List<Contestant>> by lazy {
        followedMutable
    }

    init {
        loadMatches()
    }

    private fun loadMatches() {
        Api.call(ApiClient.apiDao.getResult(), object : Observer<ApiResponse>{
            override fun onNext(response: ApiResponse) {
                allMatches = ArrayList(response.results)
                refreshCalendar()
                refreshFollowedTeams()
            }
            override fun onSubscribe(d: Disposable) {}
            override fun onError(e: Throwable) {}
            override fun onComplete() {}
        })
    }

    fun searchFavoriteTeams(term: String){
        followedMutable.value = allMatches.getContestants()
            .filter { it.isFollowed() }
            .filter { it.name.contains(term, true) }
    }

    fun unfollow(contestant: Contestant){
        contestant.unfollow()
        refreshFollowedTeams()
    }

    fun refreshFollowedTeams(){
        val teams = allMatches.getContestants()
            .filter { it.isFollowed() }
        followedMutable.value = teams
    }

    fun searchCalendar(term: String){
        dateFilter.value?.let { date ->
            calendarMutable.value = allMatches
                .byDate(date)
                .filter { match ->
                    (match.competition.name.contains(term, true) ) ||
                            (match.contestant.any {
                                it.name.contains(term, true)
                    })
                }
                .orderByTime()
                .toCalendarItems()
        }
    }

    fun nextDay(){
        cal.add(Calendar.DAY_OF_MONTH, 1)
        dateFilterMutable.value = cal.time
        refreshCalendar()
    }

    fun previousDay(){
        cal.add(Calendar.DAY_OF_MONTH, -1)
        dateFilterMutable.value = cal.time
        refreshCalendar()
    }

    fun live(){
        cal = Calendar.getInstance()
        dateFilterMutable.value = cal.today()
        calendarMutable.value = allMatches
            .live()
            .orderByTime()
            .toCalendarItems()
    }

    private fun refreshCalendar(){
        dateFilterMutable.value?.let { date->
            calendarMutable.value = allMatches
                .byDate(date)
                .orderByTime()
                .toCalendarItems()
        }
    }
}