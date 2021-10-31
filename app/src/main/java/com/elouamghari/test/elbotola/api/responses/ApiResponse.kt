package com.elouamghari.test.elbotola.api.responses

import com.elouamghari.test.elbotola.api.models.Match
import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("results") val results: List<Match>,
)