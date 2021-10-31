package com.elouamghari.test.elbotola.utils

import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.view.Menu
import androidx.annotation.IdRes
import androidx.appcompat.widget.SearchView

object MenuSearchViewUtils {
    fun setup(activity: Activity, menu: Menu, @IdRes searchId: Int, searchListener: SearchView.OnQueryTextListener){
        val searchItem = menu.findItem(searchId)
        val searchView = searchItem?.actionView as SearchView?
        searchView?.let {
            val searchManager = activity.getSystemService(Context.SEARCH_SERVICE) as SearchManager
            searchView.setSearchableInfo(searchManager.getSearchableInfo(activity.componentName))
            searchView.setOnQueryTextListener(searchListener)
        }
    }
}