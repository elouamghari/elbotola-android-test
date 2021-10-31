package com.elouamghari.test.elbotola.api.models

import com.google.gson.annotations.SerializedName

data class Score(
    @SerializedName("ht") val ht: MatchResult?,
    @SerializedName("ft") val ft: MatchResult,
    @SerializedName("total") val total: MatchResult?
)