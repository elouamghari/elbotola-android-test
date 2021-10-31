package com.elouamghari.test.elbotola.api.models

import com.google.gson.annotations.SerializedName

data class Match(
    @SerializedName("match_id") val matchId: String,
    @SerializedName("date") val date: String,
    @SerializedName("time") val time: String,
    @SerializedName("competition") val competition: Competition,
    @SerializedName("contestant") val contestant: List<Contestant>,
    @SerializedName("match_details") val matchDetails: MatchDetails
)