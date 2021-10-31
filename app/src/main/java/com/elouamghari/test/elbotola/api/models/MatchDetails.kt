package com.elouamghari.test.elbotola.api.models

import com.google.gson.annotations.SerializedName

data class MatchDetails(
    @SerializedName("match_status") val matchStatus: String,
    @SerializedName("match_time") val matchTime: Int? = null,
    @SerializedName("winner") val winner: String,
    @SerializedName("scores") val scores: Score? = null
)
