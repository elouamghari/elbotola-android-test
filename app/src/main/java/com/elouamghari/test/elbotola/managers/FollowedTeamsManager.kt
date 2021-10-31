package com.elouamghari.test.elbotola.managers

import com.elouamghari.test.elbotola.api.models.Contestant
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.Exception

object FollowedTeamsManager {

    private const val FAVORITE_PREFERENCE_KEY = "com.elouamghari.test.elbotola.FavoriteTeams"

    private fun getFollowedTeams(): List<String>{
        val data = AppPreferenceManager.getPreference(FAVORITE_PREFERENCE_KEY)
        data?.let { json ->
            return try {
                Gson().fromJson(json, object : TypeToken<List<String>>() {}.type)
            } catch (ex: Exception){
                ArrayList()
            }
        }
        return ArrayList()
    }

    fun follow(contestant: Contestant){
        val favorites = getFollowedTeams().toMutableList()
        favorites.add(contestant.id)
        val json = Gson().toJson(favorites)
        AppPreferenceManager.setPreference(FAVORITE_PREFERENCE_KEY, json)
    }

    fun unfollow(contestant: Contestant){
        val favorites = getFollowedTeams().toMutableList()
        favorites.remove(contestant.id)
        val json = Gson().toJson(favorites)
        AppPreferenceManager.setPreference(FAVORITE_PREFERENCE_KEY, json)
    }

    fun isFollowed(contestant: Contestant): Boolean{
        val favorites = getFollowedTeams()
        return favorites.contains(contestant.id)
    }

}