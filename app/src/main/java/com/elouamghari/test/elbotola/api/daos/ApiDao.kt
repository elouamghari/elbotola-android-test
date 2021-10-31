package com.elouamghari.test.elbotola.api.daos

import com.elouamghari.test.elbotola.api.constants.ApiConstants
import com.elouamghari.test.elbotola.api.responses.ApiResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface ApiDao {

    @GET(ApiConstants.END_POINT)
    fun getResult() : Observable<ApiResponse>

}