package com.elouamghari.test.elbotola.api.models

import com.google.gson.annotations.SerializedName

data class Contestant(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("position") val position: String
){

    fun getImage(): String{
        return "https://images.elbotola.com/stats/logos/$id.png"
    }

}
