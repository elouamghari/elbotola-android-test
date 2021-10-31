package com.elouamghari.test.elbotola.api.models

import com.google.gson.annotations.SerializedName

data class MatchResult (
    @SerializedName("home") val home: Int,
    @SerializedName("away") val away: Int
)