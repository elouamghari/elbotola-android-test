package com.elouamghari.test.elbotola.managers

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.elouamghari.test.elbotola.api.models.Contestant
import com.elouamghari.test.elbotola.applications.MainApplication

object AppPreferenceManager {

    private fun getSharedPreference(): SharedPreferences {
        return  PreferenceManager.getDefaultSharedPreferences(MainApplication.context)
    }

    fun setPreference(key: String, value: String?){
        val pref = getSharedPreference()
        val edit = pref.edit()
        edit.putString(key, value)
        edit.apply()
    }

    fun getPreference(key: String): String?{
        val pref = getSharedPreference()
        return pref.getString(key, null)
    }
}